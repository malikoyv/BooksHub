package com.malikoyv.client.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malikoyv.client.contract.AuthorDto;
import com.malikoyv.core.model.Author;
import com.malikoyv.core.repositories.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class AuthorMapper implements IMapper<AuthorDto, Author> {
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

        author.setBirthDate(parseDate(authorDto.birthDate()));
        author.setDeathDate(parseDate(authorDto.deathDate()));

        return author;
    }

    private static final List<DateTimeFormatter> DATE_FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("d MMMM yyyy"),      // Example: 8 February 1819
            DateTimeFormatter.ofPattern("MMMM d, yyyy"),     // Example: February 8, 1819
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),       // Example: 1819-02-08
            DateTimeFormatter.ofPattern("d-MMM-yyyy"),       // Example: 8-Feb-1819
            DateTimeFormatter.ofPattern("dd/MM/yyyy")        // Example: 08/02/1819
    );

    private LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }

        // Check if the date string is a four-digit year
        if (dateString.matches("\\d{4}")) {
            int year = Integer.parseInt(dateString);
            // Default to January 1st of the extracted year
            return LocalDate.of(year, 1, 1);
        }

        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        System.err.println("Unable to parse date: " + dateString);
        return null;
    }
}