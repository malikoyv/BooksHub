package com.malikoyv.api.controllers;

import com.malikoyv.client.contract.AuthorDto;
import com.malikoyv.api.services.AuthorService;
import com.malikoyv.core.model.Author;
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
    public Author createAuthor(@RequestBody AuthorDto dto) {
        return authorService.saveAuthor(dto);
    }

    @GetMapping("/{key}")
    public AuthorDto getAuthor(@PathVariable String key) {
        return authorService.getAuthor(key);
    }

    @GetMapping
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PutMapping("/{key}")
    public void updateAuthor(@PathVariable String key, @RequestBody AuthorDto dto) {
        authorService.updateAuthor(key, dto);
    }

    @PostMapping("/fetch/{query}")
    public void fetchAuthors(@PathVariable String query) {
        authorService.updateAuthorsFromOpenLibrary(query);
    }

    @DeleteMapping("/{key}")
    public void deleteAuthor(@PathVariable String key) {
        authorService.deleteAuthor(key);
    }
}

