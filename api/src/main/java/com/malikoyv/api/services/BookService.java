package com.malikoyv.api.services;

import com.malikoyv.client.IBooksClient;
import com.malikoyv.client.contract.BookPagedResultDto;
import com.malikoyv.client.mappers.BookMapper;
import com.malikoyv.core.model.Author;
import com.malikoyv.core.model.Book;
import com.malikoyv.client.contract.BookDto;
import com.malikoyv.core.model.Subject;
import com.malikoyv.core.repositories.ICatalogData;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final IBooksClient booksClient;
    private final BookMapper bookMapper;
    private final ICatalogData db;

    public BookService(IBooksClient booksClient, BookMapper bookMapper, ICatalogData db) {
        this.booksClient = booksClient;
        this.bookMapper = bookMapper;
        this.db = db;
    }

    public long saveBook(BookDto dto) {
        var bookEntity = new Book();
        bookEntity.setTitle(dto.title());
        bookEntity.setKey(dto.key());
        bookEntity.setPublishDate(LocalDate.parse(dto.firstPublishDate()));

        db.getBooks().save(bookEntity);
        return bookEntity.getId();
    }

    public BookDto getBook(String key) {
        var book = db.getBooks().findByKey(key);
        return toDto(book);
    }

    public List<BookDto> getAllBooks() {
        return db.getBooks().findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public void updateBook(String key, BookDto dto) {
        var book = db.getBooks().findByKey(key);
        book.setTitle(dto.title());
        book.setPublishDate(LocalDate.parse(dto.firstPublishDate()));
        book.setKey(dto.key());

        db.getBooks().save(book);
    }

    public void deleteBook(String key) {
        db.getBooks().deleteByKey(key);
    }

    public void updateBooksFromOpenLibrary(String query) {
        BookPagedResultDto response = booksClient.searchBooks(query, 1);

        if (response != null && response.books() != null) {
            List<BookDto> books = response.books();
            for (BookDto dto : books) {
                Book book = bookMapper.map(dto);
                db.getBooks().save(book);
                System.out.println("Saved/Updated book: " + book.getTitle());
            }
        }
    }

    private BookDto toDto(Book book) {
        String publishDateStr = book.getPublishDate() != null ? book.getPublishDate().toString() : "Unknown";
        return new BookDto(
                book.getKey(),
                book.getTitle(),
                book.getAuthors().stream().map(Author::getName).collect(Collectors.toList()),
                publishDateStr,
                book.getSubjects().stream().map(Subject::toString).collect(Collectors.toList())
        );
    }
}
