package com.malikoyv.api.services;

import com.malikoyv.core.model.Book;
import com.malikoyv.api.contract.BookDto;
import com.malikoyv.core.repositories.ICatalogData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final ICatalogData db;

    public BookService(ICatalogData db) {
        this.db = db;
    }

    public long saveBook(BookDto dto) {
        var bookEntity = new Book();
        bookEntity.setTitle(dto.title());
        bookEntity.setPublishDate(dto.publishDate());
        bookEntity.setDescription(dto.description());
        bookEntity.setPageCount(dto.pageCount());
        bookEntity.setLanguage(dto.language());

        db.getBooks().save(bookEntity);
        return bookEntity.getId();
    }

    public BookDto getBook(long id) {
        var book = db.getBooks().findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        return toDto(book);
    }

    public List<BookDto> getAllBooks() {
        return db.getBooks().findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public void updateBook(long id, BookDto dto) {
        var book = db.getBooks().findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(dto.title());
        book.setPublishDate(dto.publishDate());
        book.setDescription(dto.description());
        book.setPageCount(dto.pageCount());
        book.setLanguage(dto.language());

        db.getBooks().save(book);
    }

    public void deleteBook(long id) {
        db.getBooks().deleteById(id);
    }

    private BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getPublishDate(),
                book.getDescription(),
                book.getPageCount(),
                book.getLanguage()
        );
    }
}
