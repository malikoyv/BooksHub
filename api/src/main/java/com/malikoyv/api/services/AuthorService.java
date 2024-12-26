package com.malikoyv.api.services;

import com.malikoyv.client.IBooksClient;
import com.malikoyv.client.contract.AuthorSearchResponse;
import com.malikoyv.client.mappers.AuthorMapper;
import com.malikoyv.core.model.Author;
import com.malikoyv.client.contract.AuthorDto;
import com.malikoyv.core.repositories.ICatalogData;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final IBooksClient booksClient;
    private final AuthorMapper authorMapper;
    private final ICatalogData db;

    public AuthorService(IBooksClient booksClient, AuthorMapper authorMapper, ICatalogData db) {
        this.booksClient = booksClient;
        this.authorMapper = authorMapper;
        this.db = db;
    }

    public long saveAuthor(AuthorDto dto) {
        var author = new Author();
        author.setName(dto.name());
        author.setKey(dto.key());
        author.setAlternateNames(dto.alternateNames());
        author.setTopSubjects(dto.topSubjects());
        author.setTopWork(dto.topWork());
        author.setWorkCount(dto.workCount());
        author.setBirthDate(LocalDate.parse(dto.birthDate()));

        db.getAuthors().save(author);
        return author.getId();
    }

    public AuthorDto getAuthor(long id) {
        var author = db.getAuthors().findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        return toDto(author);
    }

    public List<AuthorDto> getAllAuthors() {
        return db.getAuthors().findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public void updateAuthorsFromOpenLibrary() {
        AuthorSearchResponse response = booksClient.searchAuthors("j");

        if (response != null && response.authors() != null) {
            List<AuthorDto> authors = response.authors();
            for (AuthorDto dto : authors) {
                Author author = authorMapper.map(dto);
                db.getAuthors().save(author);
                System.out.println("Saved/Updated author: " + author.getName());
            }
        }
    }

    private AuthorDto toDto(Author author) {
        return new AuthorDto(
                author.getName(),
                author.getKey(),
                author.getAlternateNames(),
                author.getBirthDate().toString(),
                author.getDeathDate().toString(),
                author.getTopSubjects(),
                author.getTopWork(),
                author.getWorkCount()
        );
    }
}

