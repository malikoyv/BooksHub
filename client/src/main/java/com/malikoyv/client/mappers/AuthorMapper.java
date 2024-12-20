package com.malikoyv.client.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malikoyv.client.contract.AuthorDto;
import com.malikoyv.core.model.Author;
import com.malikoyv.core.repositories.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AuthorMapper implements IMapper<AuthorDto, Author>{
    @Override
    public Author map(AuthorDto authorDto) {
        return map(authorDto, new Author());
    }

    @Override
    public Author map(AuthorDto authorDto, Author author) {
        author.setName(authorDto.name());
        author.setKey(authorDto.key());
        author.setAlternateNames(authorDto.alternateNames());
        author.setTopSubjects(authorDto.topSubjects());
        author.setTopWork(authorDto.topWork());
        author.setWorkCount(authorDto.workCount());

        if (authorDto.birthDate() != null) {
            author.setBirthDate(LocalDate.parse(authorDto.birthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if (authorDto.deathDate() != null) {
            author.setDeathDate(LocalDate.parse(authorDto.deathDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        return author;
    }
}