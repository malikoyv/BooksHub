package com.malikoyv.client.services;


import com.malikoyv.api.services.RatingService;
import com.malikoyv.client.IBooksClient;
import com.malikoyv.client.contract.RatingDto;
import com.malikoyv.client.contract.RatingDto.CountsDto;
import com.malikoyv.client.contract.RatingDto.SummaryDto;
import com.malikoyv.core.model.*;
import com.malikoyv.core.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingServiceTest {

    @Mock
    private ICatalogData catalogData;

    @Mock
    private IBooksClient booksClient;

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private IRatingRepository ratingRepository;

    @Mock
    private IBookRepository bookRepository;

    @Mock
    private ISummaryRepository summaryRepository;

    @Mock
    private ICountRepository countRepository;

    @Mock
    private Rating rating;

    @Mock
    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(catalogData.getRatings()).thenReturn(ratingRepository);
        when(catalogData.getBooks()).thenReturn(bookRepository);
        when(catalogData.getSummaries()).thenReturn(summaryRepository);
        when(catalogData.getCounts()).thenReturn(countRepository);
    }

    @Test
    void getRatingByBookKey_WhenRatingExists_ShouldReturnRatingDto() {
        String bookKey = "key123";
        when(catalogData.getRatings().findByBookKey(bookKey)).thenReturn(rating);
        when (rating.getSummary()).thenReturn(new Summary(4.5, 100, 4.7));
        when (rating.getCounts()).thenReturn(new Counts(10, 20, 30, 25, 15));

        RatingDto expectedDto = new RatingDto(
                new SummaryDto(4.5, 100, 4.7),
                new CountsDto(10, 20, 30, 25, 15)
        );

        RatingDto actualDto = ratingService.getRatingByBookKey(bookKey);

        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);
        verify(catalogData.getRatings(), times(1)).findByBookKey(bookKey);
    }

    @Test
    void getRatingByBookKey_WhenRatingDoesNotExist_ShouldReturnDefaultDto() {
        String bookKey = "key123";
        when(catalogData.getRatings().findByBookKey(bookKey)).thenReturn(null);

        RatingDto actualDto = ratingService.getRatingByBookKey(bookKey);

        assertNotNull(actualDto);
        assertEquals(0.0, actualDto.summaryDto().average());
        assertEquals(0, actualDto.countsDto().oneStar());
        verify(catalogData.getRatings(), times(1)).findByBookKey(bookKey);
    }

    @Test
    void updateRatingByBookKey_WhenBookExists_ShouldUpdateRating() {
        String bookKey = "key123";
        when(catalogData.getBooks().findByKey(bookKey)).thenReturn(Optional.of(book));
        when(book.getKey()).thenReturn("books/key123");

        RatingDto mockResponse = new RatingDto(
                new SummaryDto(4.5, 100, 4.7),
                new CountsDto(10, 20, 30, 25, 15)
        );
        when(booksClient.getRatings("key123")).thenReturn(mockResponse);

        when(catalogData.getRatings().findByBookKey(bookKey)).thenReturn(null);

        ratingService.updateRatingByBookKey(bookKey);

        ArgumentCaptor<Rating> ratingCaptor = ArgumentCaptor.forClass(Rating.class);
        verify(catalogData.getRatings(), times(1)).save(ratingCaptor.capture());

        Rating savedRating = ratingCaptor.getValue();
        assertNotNull(savedRating);
        assertEquals(book, savedRating.getBook());
    }

    @Test
    void updateRatingByBookKey_WhenRatingExists_ShouldUpdateExistingRating() {
        String bookKey = "key123";
        Rating existingRating = mock(Rating.class);
        when(book.getKey()).thenReturn("books/key123");
        when(catalogData.getBooks().findByKey(bookKey)).thenReturn(Optional.of(book));
        when(catalogData.getRatings().findByBookKey(bookKey)).thenReturn(existingRating);

        RatingDto mockResponse = new RatingDto(
                new SummaryDto(4.5, 100, 4.7),
                new CountsDto(10, 20, 30, 25, 15)
        );
        when(booksClient.getRatings("key123")).thenReturn(mockResponse);

        ratingService.updateRatingByBookKey(bookKey);

        verify(existingRating, times(1)).setCounts(any(Counts.class));
        verify(existingRating, times(1)).setSummary(any(Summary.class));
        verify(catalogData.getRatings(), times(1)).save(existingRating);
    }

    @Test
    void updateRatingByBookKey_WhenBookDoesNotExist_ShouldDoNothing() {
        String bookKey = "key123";
        when(catalogData.getBooks().findByKey(bookKey)).thenReturn(Optional.empty());

        ratingService.updateRatingByBookKey(bookKey);

        verifyNoInteractions(booksClient);
        verify(catalogData.getRatings(), never()).save(any(Rating.class));
    }
}

