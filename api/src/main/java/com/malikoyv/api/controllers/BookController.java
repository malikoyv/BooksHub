package com.malikoyv.api.controllers;

import com.malikoyv.api.contract.BookDto;
import com.malikoyv.api.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public long createBook(@RequestBody BookDto dto) {
        return bookService.saveBook(dto);
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable long id) {
        return bookService.getBook(id);
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable long id, @RequestBody BookDto dto) {
        bookService.updateBook(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
    }
}

