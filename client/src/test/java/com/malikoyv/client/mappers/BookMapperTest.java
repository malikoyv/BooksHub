package com.malikoyv.client.mappers;

import com.malikoyv.client.contract.BookDto;
import com.malikoyv.core.model.Book;
import com.malikoyv.core.model.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookMapperTest {

    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapper();
    }

    @Test
    void testMap_BookDtoToBook() {
        BookDto bookDto = new BookDto(
                "/books/OL12345M",
                "Pride and Prejudice",
                List.of("Jane Austen"),
                "1813",
                List.of("Romance", "Classic", "Fiction"),
                null
        );

        Book book = bookMapper.map(bookDto);

        assertThat(book).isNotNull();
        assertThat(book.getKey()).isEqualTo("OL12345M");
        assertThat(book.getTitle()).isEqualTo("Pride and Prejudice");
        assertThat(book.getPublishDate()).isEqualTo(1813);
        assertThat(book.getSubjects())
                .extracting(Subject::getName)
                .containsExactly("Romance", "Classic", "Fiction");
    }

    @Test
    void testMap_BookDtoWithLongSubjects() {
        String longSubject = "A".repeat(300);
        BookDto bookDto = new BookDto(
                "/books/OL54321M",
                "War and Peace",
                List.of("Leo Tolstoy"),
                "1869",
                List.of(longSubject),
                null
        );

        // When
        Book book = bookMapper.map(bookDto);

        // Then
        assertThat(book).isNotNull();
        assertThat(book.getKey()).isEqualTo("OL54321M");
        assertThat(book.getTitle()).isEqualTo("War and Peace");
        assertThat(book.getPublishDate()).isEqualTo(1869);
        assertThat(book.getSubjects()).hasSize(1);
        assertThat(book.getSubjects().getFirst().getName()).hasSize(255); // Should truncate to 255 characters
    }

    @Test
    void testMap_BookDtoWithNullSubjects() {
        BookDto bookDto = new BookDto(
                "/books/OL67890M",
                "The Great Gatsby",
                List.of("F. Scott Fitzgerald"),
                "1925",
                null,
                null
        );

        Book book = bookMapper.map(bookDto);

        assertThat(book).isNotNull();
        assertThat(book.getKey()).isEqualTo("OL67890M");
        assertThat(book.getTitle()).isEqualTo("The Great Gatsby");
        assertThat(book.getPublishDate()).isEqualTo(1925);
        assertThat(book.getSubjects()).isEmpty();
    }

    @Test
    void testExtractKey_FullKey() {
        String fullKey = "/books/OL12345M";

        String key = bookMapper.extractKey(fullKey);

        assertThat(key).isEqualTo("OL12345M");
    }

    @Test
    void testExtractKey_KeyWithoutSlash() {
        String fullKey = "OL12345M";

        String key = bookMapper.extractKey(fullKey);

        assertThat(key).isEqualTo("OL12345M");
    }

    @Test
    void testExtractKey_NullKey() {
        String fullKey = null;

        String key = bookMapper.extractKey(fullKey);

        assertThat(key).isNull();
    }

    @Test
    void testMap_BookDtoWithInvalidPublishDate() {
        BookDto bookDto = new BookDto(
                "/books/OL112233M",
                "1984",
                List.of("George Orwell"),
                "invalid-year",
                List.of("Dystopia", "Political Fiction"),
                null
        );

        try {
            bookMapper.map(bookDto);
        } catch (NumberFormatException e) {
            assertThat(e).hasMessageContaining("invalid-year");
        }
    }
}
