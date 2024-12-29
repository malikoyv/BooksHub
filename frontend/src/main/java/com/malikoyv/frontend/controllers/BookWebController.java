package com.malikoyv.frontend.controllers;

import com.malikoyv.client.contract.AuthorDto;
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

    @GetMapping
    public String listBooks(Model model) {
        BookDto[] booksArray = restTemplate.getForObject("http://localhost:8080/api/books", BookDto[].class);
        List<BookDto> books = Arrays.asList(booksArray);
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/create")
    public String createBookForm(Model model) {
        AuthorDto[] authorsArray = restTemplate.getForObject("http://localhost:8080/api/authors", AuthorDto[].class);
        List<AuthorDto> authors = Arrays.asList(authorsArray);

        model.addAttribute("book", new BookDto("", "", List.of(), "", List.of("")));
        model.addAttribute("allAuthors", authors);
        model.addAttribute("action", "create");
        return "books/create";
    }

    @GetMapping("/{key}/update")
    public String updateBookForm(@PathVariable String key, Model model) {
        BookDto book = restTemplate.getForObject("http://localhost:8080/api/books/" + key, BookDto.class);
        AuthorDto[] authorsArray = restTemplate.getForObject("http://localhost:8080/api/authors", AuthorDto[].class);
        List<AuthorDto> authors = Arrays.asList(authorsArray);

        model.addAttribute("book", book);
        model.addAttribute("allAuthors", authors);
        model.addAttribute("action", "update");
        return "books/update";
    }

    @PostMapping
    public String createBook(@ModelAttribute BookDto book) {
        restTemplate.postForObject("http://localhost:8080/api/books", book, Long.class);
        return "redirect:/books";
    }

    @PutMapping("/{key}")
    public String updateBook(@PathVariable String key, @ModelAttribute BookDto book) {
        restTemplate.put("http://localhost:8080/api/books/" + key, book);
        return "redirect:/books";
    }
}
