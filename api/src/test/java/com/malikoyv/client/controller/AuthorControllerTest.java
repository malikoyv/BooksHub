package com.malikoyv.client.controller;

import com.malikoyv.api.controllers.AuthorController;
import com.malikoyv.client.contract.AuthorDto;
import com.malikoyv.api.services.AuthorService;
import com.malikoyv.core.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuthorControllerTest {

    private AuthorController authorController;
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        authorService = mock(AuthorService.class);
        authorController = new AuthorController(authorService);
    }

    @Test
    void createAuthor_shouldReturnAuthor() {
        AuthorDto dto = new AuthorDto("Test Author", "test-key", List.of("Alternate Name"),
                "1990-01-01", "2020-01-01", List.of("Fiction"), "Top Work", 10);

        Author author = new Author();
        when(authorService.saveAuthor(dto)).thenReturn(author);

        Author createdAuthor = authorController.createAuthor(dto);

        assertThat(createdAuthor).isNotNull();
        verify(authorService, times(1)).saveAuthor(dto);
    }

    @Test
    void getAuthor_shouldReturnAuthorDto() {
        AuthorDto expectedDto = new AuthorDto("Test Author", "test-key", List.of("Alternate Name"),
                "1990-01-01", "2020-01-01", List.of("Fiction"), "Top Work", 10);

        when(authorService.getAuthor("test-key")).thenReturn(expectedDto);

        AuthorDto actualDto = authorController.getAuthor("test-key");

        assertThat(actualDto).isEqualTo(expectedDto);
        verify(authorService, times(1)).getAuthor("test-key");
    }

    @Test
    void updateAuthor_shouldUpdateAuthor() {
        AuthorDto dto = new AuthorDto("Updated Author", "test-key", List.of("Alternate Name"),
                "1990-01-01", "2020-01-01", List.of("Fiction"), "Updated Work", 20);

        doNothing().when(authorService).updateAuthor("test-key", dto);

        authorController.updateAuthor("test-key", dto);

        verify(authorService, times(1)).updateAuthor("test-key", dto);
    }

    @Test
    void deleteAuthor_shouldDeleteAuthor() {
        doNothing().when(authorService).deleteAuthor("test-key");

        authorController.deleteAuthor("test-key");

        verify(authorService, times(1)).deleteAuthor("test-key");
    }
}

