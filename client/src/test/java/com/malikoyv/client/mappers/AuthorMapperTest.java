package com.malikoyv.client.mappers;

import com.malikoyv.client.contract.AuthorDto;
import com.malikoyv.core.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorMapperTest {

    private AuthorMapper authorMapper;

    @BeforeEach
    void setUp() {
        authorMapper = new AuthorMapper();
    }

    @Test
    void testMap_AuthorDtoToAuthor() {
        // Given
        AuthorDto authorDto = new AuthorDto(
                "Jane Austen",
                "12345",
                List.of("J. Austen", "Jane A."),
                "16 December 1775",
                "18 July 1817",
                List.of("Literature", "Romance"),
                "Pride and Prejudice",
                7
        );

        Author author = authorMapper.map(authorDto);

        assertThat(author).isNotNull();
        assertThat(author.getName()).isEqualTo("Jane Austen");
        assertThat(author.getKey()).isEqualTo("12345");
        assertThat(author.getAlternateNames()).containsExactly("J. Austen", "Jane A.");
        assertThat(author.getBirthDate()).isEqualTo(LocalDate.of(1775, 12, 16));
        assertThat(author.getDeathDate()).isEqualTo(LocalDate.of(1817, 7, 18));
        assertThat(author.getTopSubjects()).containsExactly("Literature", "Romance");
        assertThat(author.getTopWork()).isEqualTo("Pride and Prejudice");
        assertThat(author.getWorkCount()).isEqualTo(7);
    }

    @Test
    void testMap_AuthorDtoWithFourDigitYear() {
        AuthorDto authorDto = new AuthorDto(
                "William Shakespeare",
                "54321",
                List.of("W. Shakespeare"),
                "1564",
                "1616",
                List.of("Drama", "Poetry"),
                "Hamlet",
                39
        );

        Author author = authorMapper.map(authorDto);

        assertThat(author).isNotNull();
        assertThat(author.getBirthDate()).isEqualTo(LocalDate.of(1564, 1, 1)); // Defaults to January 1st
        assertThat(author.getDeathDate()).isEqualTo(LocalDate.of(1616, 1, 1)); // Defaults to January 1st
    }

    @Test
    void testMap_AuthorDtoWithInvalidDate() {
        // Given
        AuthorDto authorDto = new AuthorDto(
                "Charles Dickens",
                "67890",
                List.of("C. Dickens"),
                "invalid-date",
                null,
                List.of("Fiction", "Social Criticism"),
                "Oliver Twist",
                15
        );

        Author author = authorMapper.map(authorDto);

        assertThat(author).isNotNull();
        assertThat(author.getBirthDate()).isNull(); // Invalid date should result in null
        assertThat(author.getDeathDate()).isNull(); // Null in DTO should result in null
    }
}
