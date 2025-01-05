package com.malikoyv.client.controller;

import com.malikoyv.api.controllers.BookController;
import com.malikoyv.api.services.BookService;
import com.malikoyv.client.contract.BookDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void createBook_ShouldReturnBookId() throws Exception {
        BookDto dto = new BookDto("key123", "Book Title", null, null, null, null);
        when(bookService.saveBook(any(BookDto.class))).thenReturn(1L);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "key": "key123",
                        "title": "Book Title",
                        "authors": ["Author"],
                        "firstPublishDate": "2023",
                        "subjects": ["Subject"]
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(bookService).saveBook(dto);
    }

    @Test
    void getBook_ShouldReturnBook() throws Exception {
        BookDto dto = new BookDto("key123", "Book Title", List.of("Author"), "2023", List.of("Subject"), null);
        when(bookService.getBook("key123")).thenReturn(dto);

        mockMvc.perform(get("/api/books/key123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key").value("key123"))
                .andExpect(jsonPath("$.title").value("Book Title"));

        verify(bookService).getBook("key123");
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() throws Exception {
        List<BookDto> books = List.of(
                new BookDto("key123", "Book Title", List.of("Author"), "2023", List.of("Subject"), null),
                new BookDto("key456", "Another Book", List.of("Another Author"), "2022", List.of("Another Subject"), null)
        );
        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].key").value("key123"))
                .andExpect(jsonPath("$[1].key").value("key456"));

        verify(bookService).getAllBooks();
    }

    @Test
    void updateBook_ShouldCallService() throws Exception {
        BookDto dto = new BookDto("key123", "Updated Title", null, null, null, null);

        mockMvc.perform(put("/api/books/key123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "key": "key123",
                        "title": "Updated Title",
                        "authors": ["Updated Author"],
                        "firstPublishDate": "2024",
                        "subjects": ["Updated Subject"]
                    }
                """))
                .andExpect(status().isOk());

        verify(bookService).updateBook("key123", dto);
    }

    @Test
    void deleteBook_ShouldCallService() throws Exception {
        mockMvc.perform(delete("/api/books/key123"))
                .andExpect(status().isOk());

        verify(bookService).deleteBook("key123");
    }

    @Test
    void updateBooks_ShouldCallService() throws Exception {
        mockMvc.perform(post("/api/books/fetch/key123"))
                .andExpect(status().isOk());

        verify(bookService).updateBooksFromOpenLibrary("key123");
    }
}

