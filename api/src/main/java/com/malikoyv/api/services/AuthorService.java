package com.malikoyv.api.services;

import com.malikoyv.core.model.Author;
import com.malikoyv.api.contract.AuthorDto;
import com.malikoyv.core.repositories.ICatalogData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final ICatalogData db;

    public AuthorService(ICatalogData db) {
        this.db = db;
    }

    public long saveAuthor(AuthorDto dto) {
        var author = new Author();
        author.setName(dto.name());
        author.setBirthDate(dto.birthday());
        author.setBiography(dto.biography());

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

    private AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName(), author.getBirthDate(), author.getBiography());
    }
}

