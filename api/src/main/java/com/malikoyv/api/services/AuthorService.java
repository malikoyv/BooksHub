package com.malikoyv.api.services;

import com.malikoyv.client.IBooksClient;
import com.malikoyv.client.contract.AuthorSearchResponse;
import com.malikoyv.client.mappers.AuthorMapper;
import com.malikoyv.core.model.Author;
import com.malikoyv.client.contract.AuthorDto;
import com.malikoyv.core.repositories.ICatalogData;
import jakarta.persistence.EntityNotFoundException;
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

    public Author saveAuthor(AuthorDto dto) {
        var author = new Author();
        setAllFieldsAndSave(author, dto);
        return author;
    }

    public AuthorDto getAuthor(String key) {
        Author author = db.getAuthors().findByKey(key)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with key: " + key));

        return toDto(author);
    }

    public List<AuthorDto> getAllAuthors() {
        return db.getAuthors().findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public void updateAuthor(String key, AuthorDto dto){
        var author = db.getAuthors().findByKey(key).orElseThrow(() -> new RuntimeException("Author not found"));
        setAllFieldsAndSave(author, dto);
    }

    public void updateAuthorsFromOpenLibrary(String query) {
        AuthorSearchResponse response = booksClient.searchAuthors(query);

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
                author.getBirthDate() != null ? author.getBirthDate().toString() : null,
                author.getDeathDate() != null ? author.getDeathDate().toString() : null,
                author.getTopSubjects(),
                author.getTopWork(),
                author.getWorkCount()
        );
    }

    private void setAllFieldsAndSave(Author author, AuthorDto dto) {
        author.setName(dto.name() != null ? dto.name() : "Unknown");
        author.setKey(dto.key());
        author.setAlternateNames(dto.alternateNames());
        author.setTopSubjects(dto.topSubjects());
        author.setTopWork(dto.topWork());

        author.setWorkCount(dto.workCount() != null ? dto.workCount() : 0);

        author.setBirthDate(dto.birthDate() != null ? LocalDate.parse(dto.birthDate()) : null);
        author.setDeathDate(!dto.deathDate().isEmpty() ? LocalDate.parse(dto.deathDate()) : null);

        db.getAuthors().save(author);
    }
}

