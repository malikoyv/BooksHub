package com.malikoyv.frontend.controllers;

import com.malikoyv.client.contract.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookWebController {

    private final RestTemplate restTemplate;

    @Autowired
    public BookWebController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/list")
    public String listBooks(Model model) {
        BookDto[] booksArray = restTemplate.getForObject("http://localhost:8080/api/books", BookDto[].class);
        List<BookDto> books = Arrays.asList(booksArray);
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/create")
    public String createBookForm(Model model) {
        model.addAttribute("book", new BookDto("", "", List.of(), "", List.of("")));
        return "books/create";
    }

    @PostMapping
    public String createBook(@ModelAttribute BookDto book) {
        restTemplate.postForObject("/api/books", book, Long.class);
        return "redirect:/books";
    }

    @GetMapping("/{id}/update")
    public String updateBookForm(@PathVariable long id, Model model) {
        BookDto book = restTemplate.getForObject("/api/books/" + id, BookDto.class);
        model.addAttribute("book", book);
        return "books/update";
    }

    @PostMapping("/{id}")
    public String updateBook(@PathVariable long id, @ModelAttribute BookDto book) {
        restTemplate.put("/api/books/" + id, book);
        return "redirect:/books";
    }
}
