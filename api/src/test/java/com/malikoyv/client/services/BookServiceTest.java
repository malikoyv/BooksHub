package com.malikoyv.client.services;

import com.malikoyv.api.exceptions.BookAlreadyExistsException;
import com.malikoyv.api.exceptions.BookNotFoundException;
import com.malikoyv.api.services.BookService;
import com.malikoyv.api.services.RatingService;
import com.malikoyv.client.IBooksClient;
import com.malikoyv.client.contract.BookDto;
import com.malikoyv.client.mappers.BookMapper;
import com.malikoyv.core.model.Book;
import com.malikoyv.core.repositories.IAuthorRepository;
import com.malikoyv.core.repositories.IBookRepository;
import com.malikoyv.core.repositories.ICatalogData;
import com.malikoyv.core.repositories.ISubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private ICatalogData mockDb;

    @Mock
    private IBookRepository bookRepository;

    private BookService bookService;
    private IBooksClient mockBooksClient;
    private BookMapper mockBookMapper;
    private RatingService mockRatingService;

    @Mock
    private IAuthorRepository authorRepository;

    @Mock
    private ISubjectRepository subjectRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock catalog data
        mockDb = mock(ICatalogData.class);
        bookRepository = mock(IBookRepository.class);
        authorRepository = mock(IAuthorRepository.class);
        subjectRepository = mock(ISubjectRepository.class);

        when(mockDb.getBooks()).thenReturn(bookRepository);
        when(mockDb.getAuthors()).thenReturn(authorRepository);
        when(mockDb.getSubjects()).thenReturn(subjectRepository);

        mockBooksClient = mock(IBooksClient.class);
        mockBookMapper = mock(BookMapper.class);
        mockRatingService = mock(RatingService.class);

        bookService = new BookService(mockBooksClient, mockBookMapper, mockRatingService, mockDb);
    }

    @Test
    void saveBook_shouldThrowExceptionIfBookExists() {
        BookDto bookDto = new BookDto("book-key", "Title", List.of("Author"), "2020", List.of("Subject"), null);

        when(mockDb.getBooks().findByKey("book-key")).thenReturn(Optional.of(new Book()));

        assertThrows(BookAlreadyExistsException.class, () -> bookService.saveBook(bookDto));
    }

    @Test
    void getBook_shouldReturnBookDto() {
        Book mockBook = new Book();
        mockBook.setKey("book-key");
        when(mockDb.getBooks().findByKey("book-key")).thenReturn(Optional.of(mockBook));

        BookDto result = bookService.getBook("book-key");

        assertNotNull(result);
        assertEquals("book-key", result.key());
        verify(mockDb.getBooks()).findByKey("book-key");
    }

    @Test
    void getBook_shouldThrowExceptionIfNotFound() {
        when(mockDb.getBooks().findByKey("non-existent-key")).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBook("non-existent-key"));
    }

    @Test
    void updateBook_shouldUpdateSuccessfully() {
        BookDto bookDto = new BookDto("new-book-key", "Updated Title", List.of("Author"), "2020", List.of("Subject"), null);
        Book existingBook = new Book();
        existingBook.setKey("existing-key");

        when(mockDb.getBooks().findByKey("existing-key")).thenReturn(Optional.of(existingBook));
        when(mockDb.getBooks().findByKey("new-book-key")).thenReturn(Optional.empty());

        bookService.updateBook("existing-key", bookDto);

        verify(mockDb.getBooks()).save(existingBook);
    }
}
