package com.malikoyv.api.controllers;

import com.malikoyv.client.contract.AuthorDto;
import com.malikoyv.api.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public long createAuthor(@RequestBody AuthorDto dto) {
        return authorService.saveAuthor(dto);
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthor(@PathVariable long id) {
        return authorService.getAuthor(id);
    }

    @GetMapping
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping("/update")
    public void updateAuthors() {
        authorService.updateAuthorsFromOpenLibrary();
    }
}

