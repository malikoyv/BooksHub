package com.malikoyv.frontend.controllers;

import com.malikoyv.client.contract.AuthorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorWebController {

    private final RestTemplate restTemplate;

    @Autowired
    public AuthorWebController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String listAuthors(Model model) {
        AuthorDto[] authorsArray = restTemplate.getForObject("http://localhost:8080/api/authors", AuthorDto[].class);
        List<AuthorDto> authors = List.of(authorsArray);
        model.addAttribute("authors", authors);
        return "authors/list";
    }

    @GetMapping("/create")
    public String createAuthorForm(Model model) {
        model.addAttribute("author", new AuthorDto(
                "",
                "",
                List.of(),
                "",
                "",
                null,
                "",
                0
        ));
        return "authors/create";
    }

    @PostMapping
    public String createAuthor(@ModelAttribute AuthorDto author) {
        restTemplate.postForObject("http://localhost:8080/api/authors", author, Long.class);
        return "redirect:/authors";
    }

    @GetMapping("/{id}/update")
    public String updateAuthorForm(@PathVariable long id, Model model) {
        AuthorDto author = restTemplate.getForObject("http://localhost:8080/api/authors/" + id, AuthorDto.class);
        model.addAttribute("author", author);
        return "authors/update";
    }

    @PostMapping("/{id}")
    public String updateAuthor(@PathVariable long id, @ModelAttribute AuthorDto author) {
        restTemplate.put("http://localhost:8080/api/authors/" + id, author);
        return "redirect:/authors";
    }
}