package com.malikoyv.api.services;

import com.malikoyv.client.IBooksClient;
import com.malikoyv.client.contract.BookPagedResultDto;
import com.malikoyv.client.contract.RatingDto;
import com.malikoyv.client.mappers.BookMapper;
import com.malikoyv.core.model.Author;
import com.malikoyv.core.model.Book;
import com.malikoyv.client.contract.BookDto;
import com.malikoyv.core.model.Rating;
import com.malikoyv.core.model.Subject;
import com.malikoyv.core.repositories.ICatalogData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final IBooksClient booksClient;
    private final BookMapper bookMapper;
    private final RatingService ratingService;
    private final ICatalogData db;

    public BookService(IBooksClient booksClient, BookMapper bookMapper, RatingService ratingService, ICatalogData db) {
        this.booksClient = booksClient;
        this.bookMapper = bookMapper;
        this.ratingService = ratingService;
        this.db = db;
    }

    public long saveBook(BookDto dto) {
        var bookEntity = new Book();
        bookEntity.setTitle(dto.title());
        bookEntity.setKey(dto.key());
        bookEntity.setPublishDate(Integer.parseInt(dto.firstPublishDate()));
        bookEntity.setAuthors(db.getAuthors().findByKeyIn(dto.authors()));

        List<Subject> subjects = new ArrayList<>();
        for (String subjectName : dto.subjects()) {
            if (subjectName.length() > 255) {
                subjectName = subjectName.substring(0, 255);
            }

            String finalSubjectName = subjectName;
            Subject subject = db.getSubjects().findByName(subjectName).orElseGet(() -> {
                Subject newSubject = new Subject(finalSubjectName);
                return db.getSubjects().save(newSubject);
            });
            subjects.add(subject);
        }

        bookEntity.setSubjects(subjects);
        db.getBooks().save(bookEntity);  // Save the book entity

        return bookEntity.getId();
    }

    public BookDto getBook(String key) {
        return toDto(db.getBooks().findByKey(key));
    }

    public List<BookDto> getAllBooks() {
        return db.getBooks().findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public void updateBook(String key, BookDto dto) {
        var book = db.getBooks().findByKey(key);
        book.setTitle(dto.title());
        book.setPublishDate(Integer.parseInt(dto.firstPublishDate()));
        book.setKey(dto.key());

        List<Subject> subjects = new ArrayList<>();
        for (String subjectName : dto.subjects()) {
            Subject subject = db.getSubjects().findByName(subjectName).orElseGet(() -> {
                Subject newSubject = new Subject(subjectName);
                return db.getSubjects().save(newSubject);
            });
            subjects.add(subject);
        }

        book.setSubjects(subjects);
        book.setAuthors(db.getAuthors().findByKeyIn(dto.authors()));

        db.getBooks().save(book);
    }

    @Transactional
    public void deleteBook(String key) {
        db.getBooks().deleteByKey(key);
    }

    public void updateBooksFromOpenLibrary(String query) {
        BookPagedResultDto response = booksClient.searchBooks(query, 1);

        if (response != null && response.books() != null) {
            List<BookDto> books = response.books();
            for (BookDto dto : books) {
                Optional<Book> existingBookOptional = Optional.ofNullable(db.getBooks().findByKey(dto.key()));
                Book book;

                if (existingBookOptional.isPresent()) {
                    book = existingBookOptional.get();
                    book.setTitle(dto.title());
                    book.setPublishDate(Integer.parseInt(dto.firstPublishDate()));
                    book.setKey(dto.key());
                } else {
                    book = bookMapper.map(dto);
                }

                List<Subject> subjects = new ArrayList<>();
                for (String subjectName : dto.subjects()) {
                    if (subjectName.length() > 255) {
                        subjectName = subjectName.substring(0, 255);
                    }
                    String finalSubjectName = subjectName;
                    Subject subject = db.getSubjects().findByName(subjectName).orElseGet(() -> {
                        Subject newSubject = new Subject(finalSubjectName);
                        return db.getSubjects().save(newSubject);
                    });
                    subjects.add(subject);
                }

                book.setSubjects(subjects);
                book.setAuthors(db.getAuthors().findByKeyIn(dto.authors()));

                db.getBooks().save(book);

                System.out.println("Saved/Updated book: " + book.getTitle());
            }
        }
    }

    private BookDto toDto(Book book) {
        return new BookDto(
                book.getKey(),
                book.getTitle(),
                book.getAuthors().stream().map(Author::getName).collect(Collectors.toList()),
                book.getPublishDate() != null ? book.getPublishDate().toString() : "N/A",
                book.getSubjects().stream().map(Subject::getName).collect(Collectors.toList()),
                book.getRating() != null ? ratingService.toDto(book.getRating()) : new RatingDto(
                        new RatingDto.SummaryDto(0.0, 0, 0.0),
                        new RatingDto.CountsDto(0, 0, 0, 0, 0)
                )
        );
    }
}