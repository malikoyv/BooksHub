package com.malikoyv.api.controllers;

import com.malikoyv.client.contract.BookDto;
import com.malikoyv.api.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public long createBook(@RequestBody BookDto dto) {
        return bookService.saveBook(dto);
    }

    @GetMapping("/{key}")
    public BookDto getBook(@PathVariable String key) {
        return bookService.getBook(key);
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PutMapping("/{key}")
    public void updateBook(@PathVariable String key, @RequestBody BookDto dto) {
        bookService.updateBook(key, dto);
    }

    @DeleteMapping("/{key}")
    public void deleteBook(@PathVariable String key) {
        bookService.deleteBook(key);
    }

    @PostMapping("/fetch/{key}")
    public void updateBooks(@PathVariable String key) {
        bookService.updateBooksFromOpenLibrary(key);
    }
}
