package com.malikoyv.client.mappers;

import com.malikoyv.client.contract.BookDto;
import com.malikoyv.core.model.Book;
import com.malikoyv.core.model.Subject;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements IMapper<BookDto, Book> {
    @Override
    public Book map(BookDto bookDto) {
        return map(bookDto, new Book());
    }

    @Override
    public Book map(BookDto bookDto, Book book) {
        String key = extractKey(bookDto.key());
        book.setKey(key);
        book.setTitle(bookDto.title());
        book.setPublishDate(Integer.parseInt(bookDto.firstPublishDate()));

        book.getSubjects().clear();

        for (String subject : bookDto.subjects()) {
            if (subject.length() > 255) {
                subject = subject.substring(0, 255);
            }
            Subject newSubject = new Subject(subject);
            book.getSubjects().add(newSubject);
        }

        return book;
    }

    public String extractKey(String fullKey) {
        if (fullKey == null) {
            return null;
        }

        int lastSlashIndex = fullKey.lastIndexOf('/');
        if (lastSlashIndex != -1) {
            return fullKey.substring(lastSlashIndex + 1);
        }
        return fullKey;
    }
}