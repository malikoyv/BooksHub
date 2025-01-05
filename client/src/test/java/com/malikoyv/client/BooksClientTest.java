package com.malikoyv.client;

import com.malikoyv.client.contract.AuthorSearchResponse;
import com.malikoyv.client.contract.BookPagedResultDto;
import com.malikoyv.client.contract.RatingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BooksClientTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private IBooksClientUriBuilderProvider provider;

    @InjectMocks
    private BooksClient booksClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchBooks() {
        String query = "test";
        int page = 1;
        String url = "https://openlibrary.org/search.json?q=test&page=1";
        BookPagedResultDto expectedResponse = new BookPagedResultDto(159729, 0, true, List.of());

        when(provider.buildSearchUrl(query, page)).thenReturn(url);
        when(restTemplate.getForEntity(url, BookPagedResultDto.class)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        BookPagedResultDto actualResponse = booksClient.searchBooks(query, page);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testSearchAuthors() {
        String authorName = "testAuthor";
        String url = "https://openlibrary.org/search/authors.json?q=test";
        AuthorSearchResponse expectedResponse = new AuthorSearchResponse(1635, 0, true, List.of());

        when(provider.buildAuthorSearchUrl(authorName)).thenReturn(url);
        when(restTemplate.getForEntity(url, AuthorSearchResponse.class)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        AuthorSearchResponse actualResponse = booksClient.searchAuthors(authorName);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetRatings() {
        String bookKey = "testBookKey";
        String url = "https://openlibrary.org/works/OL471509W/ratings.json";
        RatingDto expectedResponse = new RatingDto(
                new RatingDto.SummaryDto(4.09803921568627, 51, 3.73554034376783),
                new RatingDto.CountsDto(3, 1, 10, 11, 26));

        when(provider.buildRatingsUrl(bookKey)).thenReturn(url);
        when(restTemplate.getForEntity(url, RatingDto.class)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        RatingDto actualResponse = booksClient.getRatings(bookKey);

        assertEquals(expectedResponse, actualResponse);
    }
}
