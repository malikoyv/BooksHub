package com.malikoyv.api.services;

import com.malikoyv.client.IBooksClient;
import com.malikoyv.client.contract.AuthorDto;
import com.malikoyv.client.contract.BookPagedResultDto;
import com.malikoyv.client.contract.RatingDto;
import com.malikoyv.client.mappers.BookMapper;
import com.malikoyv.core.model.Author;
import com.malikoyv.core.model.Book;
import com.malikoyv.client.contract.BookDto;
import com.malikoyv.core.model.Subject;
import com.malikoyv.core.repositories.ICatalogData;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final IBooksClient booksClient;
    private final BookMapper bookMapper;
    private final RatingService ratingService;
    private final ICatalogData db;
    private final AuthorService authorService;

    public BookService(IBooksClient booksClient, BookMapper bookMapper, RatingService ratingService, ICatalogData db, AuthorService authorService) {
        this.booksClient = booksClient;
        this.bookMapper = bookMapper;
        this.ratingService = ratingService;
        this.db = db;
        this.authorService = authorService;
    }

    public long saveBook(BookDto dto) {
        var bookEntity = new Book();
        bookEntity.setTitle(dto.title());
        bookEntity.setKey(dto.key());
        bookEntity.setPublishDate(Integer.parseInt(dto.firstPublishDate()));
        bookEntity.setAuthors(addAuthors(dto.authors()));

        addSubjects(dto, bookEntity);
        db.getBooks().save(bookEntity);

        return bookEntity.getId();
    }

    private List<Author> addAuthors(List<String> authorNames) {
        return authorNames.stream()
                .map(authorName -> db.getAuthors().findByName(authorName)
                        .orElseGet(() -> {
                            Author newAuthor = new Author();

                            if (!authorName.isBlank()) {
                                newAuthor.setName(authorName);
                                newAuthor.setKey(generateKey(authorName));
                            } else {
                                newAuthor.setName("Unknown");
                                newAuthor.setKey(generateKey("Unknown"));
                            }

                            return db.getAuthors().save(newAuthor);
                        }))
                .collect(Collectors.toList());
    }

    private String generateKey(String authorName) {
        String sanitizedName = authorName.toLowerCase().replaceAll("[^a-z0-9]", "-").replaceAll("-+", "-");
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        return sanitizedName + "-" + uniqueId;
    }

    private void addSubjects(BookDto dto, Book bookEntity) {
        List<Subject> subjects = dto.subjects() != null
                ? dto.subjects().stream()
                .map(subjectName -> {
                    if (subjectName.length() > 255) {
                        subjectName = subjectName.substring(0, 255);
                    }
                    String finalSubjectName = subjectName;
                    return db.getSubjects().findByName(subjectName).orElseGet(() -> {
                        Subject newSubject = new Subject(finalSubjectName);
                        return db.getSubjects().save(newSubject);
                    });
                })
                .collect(Collectors.toList())
                : List.of();

        bookEntity.setSubjects(subjects);
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

        book.setAuthors(addAuthors(dto.authors()));
        addSubjects(dto, book);

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
                String key = bookMapper.extractKey(dto.key());
                Optional<Book> existingBookOptional = Optional.ofNullable(db.getBooks().findByKey(key));
                Book book;

                if (existingBookOptional.isPresent()) {
                    book = existingBookOptional.get();
                    book.setTitle(dto.title());
                    book.setPublishDate(Integer.parseInt(dto.firstPublishDate()));
                    book.setKey(key);
                } else {
                    book = bookMapper.map(dto);
                }

                book.setAuthors(addAuthors(dto.authors()));
                addSubjects(dto, book);

                db.getBooks().save(book);

                System.out.println("Saved/Updated book: " + book.getTitle());
            }
        }
    }

    private BookDto toDto(Book book) {
        return new BookDto(
                book.getKey(),
                book.getTitle(),
                book.getAuthors().stream().map(Author::getName).collect(Collectors.toList()), // Map authors to their names
                book.getPublishDate() != null ? book.getPublishDate().toString() : "N/A",
                book.getSubjects().stream().map(Subject::getName).collect(Collectors.toList()),
                book.getRating() != null ? ratingService.toDto(book.getRating()) : new RatingDto(
                        new RatingDto.SummaryDto(0.0, 0, 0.0),
                        new RatingDto.CountsDto(0, 0, 0, 0, 0)
                )
        );
    }
}
