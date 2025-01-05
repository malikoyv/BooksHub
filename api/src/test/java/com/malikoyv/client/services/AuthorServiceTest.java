package com.malikoyv.client.services;

import com.malikoyv.api.services.AuthorService;
import com.malikoyv.client.IBooksClient;
import com.malikoyv.client.contract.AuthorDto;
import com.malikoyv.client.contract.AuthorSearchResponse;
import com.malikoyv.client.mappers.AuthorMapper;
import com.malikoyv.core.model.Author;
import com.malikoyv.core.repositories.ICatalogData;
import com.malikoyv.core.repositories.IAuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    private AuthorService authorService;
    private IBooksClient booksClient;
    private AuthorMapper authorMapper;
    private ICatalogData db;
    private IAuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        booksClient = mock(IBooksClient.class);
        authorMapper = mock(AuthorMapper.class);
        db = mock(ICatalogData.class);
        authorRepository = mock(IAuthorRepository.class);

        when(db.getAuthors()).thenReturn(authorRepository);

        authorService = new AuthorService(booksClient, authorMapper, db);
    }

    @Test
    void saveAuthor_shouldSaveAndReturnAuthor() {
        AuthorDto dto = new AuthorDto("Test Author", "test-key", List.of("Alternate Name"),
                "1990-01-01", "2020-01-01", List.of("Fiction"), "Top Work", 10);
        Author author = new Author();
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author savedAuthor = authorService.saveAuthor(dto);

        assertThat(savedAuthor).isNotNull();
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void getAuthor_shouldReturnAuthorDto() {
        Author author = new Author();
        author.setKey("test-key");
        author.setName("Test Author");
        author.setBirthDate(LocalDate.of(1990, 1, 1));
        author.setDeathDate(LocalDate.of(2020, 1, 1));

        when(authorRepository.findByKey("test-key")).thenReturn(Optional.of(author));

        AuthorDto dto = authorService.getAuthor("test-key");

        assertThat(dto).isNotNull();
        assertThat(dto.name()).isEqualTo("Test Author");
    }

    @Test
    void getAuthor_shouldThrowExceptionWhenNotFound() {
        when(authorRepository.findByKey("test-key")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> authorService.getAuthor("test-key"));
    }

    @Test
    void getAllAuthors_shouldReturnListOfAuthorDtos() {
        Author author1 = new Author();
        author1.setKey("key1");
        author1.setName("Author 1");

        Author author2 = new Author();
        author2.setKey("key2");
        author2.setName("Author 2");

        when(authorRepository.findAll()).thenReturn(List.of(author1, author2));

        List<AuthorDto> authors = authorService.getAllAuthors();

        assertThat(authors).hasSize(2);
        assertThat(authors.get(0).name()).isEqualTo("Author 1");
        assertThat(authors.get(1).name()).isEqualTo("Author 2");
    }

    @Test
    void updateAuthor_shouldUpdateAndSaveAuthor() {
        Author author = new Author();
        author.setKey("test-key");

        AuthorDto dto = new AuthorDto("Updated Author", "test-key", List.of("Alternate Name"),
                "1990-01-01", "2020-01-01", List.of("Fiction"), "Updated Work", 20);

        when(authorRepository.findByKey("test-key")).thenReturn(Optional.of(author));

        authorService.updateAuthor("test-key", dto);

        verify(authorRepository, times(1)).save(author);
        assertThat(author.getName()).isEqualTo("Updated Author");
    }

    @Test
    void deleteAuthor_shouldDeleteAuthor() {
        Author author = new Author();
        author.setKey("test-key");

        when(authorRepository.findByKey("test-key")).thenReturn(Optional.of(author));

        authorService.deleteAuthor("test-key");

        verify(authorRepository, times(1)).delete(author);
    }

    @Test
    void updateAuthorsFromOpenLibrary_shouldSaveFetchedAuthors() {
        AuthorDto dto1 = new AuthorDto("Author 1", "key1", List.of(), null, null, List.of(), null, 0);
        AuthorDto dto2 = new AuthorDto("Author 2", "key2", List.of(), null, null, List.of(), null, 0);

        AuthorSearchResponse response = new AuthorSearchResponse(2, 0, true, List.of(dto1, dto2));

        Author author1 = new Author();
        author1.setKey("key1");

        Author author2 = new Author();
        author2.setKey("key2");

        when(booksClient.searchAuthors("query")).thenReturn(response);
        when(authorMapper.map(dto1)).thenReturn(author1);
        when(authorMapper.map(dto2)).thenReturn(author2);

        authorService.updateAuthorsFromOpenLibrary("query");

        verify(authorRepository, times(1)).save(author1);
        verify(authorRepository, times(1)).save(author2);
    }
}
