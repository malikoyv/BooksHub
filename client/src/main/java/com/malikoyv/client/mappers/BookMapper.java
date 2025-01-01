package com.malikoyv.client.mappers;

import com.malikoyv.client.BooksClient;
import com.malikoyv.client.BooksClientUriBuilderProvider;
import com.malikoyv.client.contract.BookDto;
import com.malikoyv.core.model.Book;
import com.malikoyv.core.model.Subject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BookMapper implements IMapper<BookDto, Book>{
    @Override
    public Book map(BookDto bookDto) {
        return map(bookDto, new Book());
    }

    @Override
    public Book map(BookDto bookDto, Book book) {
        book.setKey(bookDto.key());
        book.setTitle(bookDto.title());
        book.setPublishDate(Integer.parseInt(bookDto.firstPublishDate()));

        for (String subject : bookDto.subjects()) {
            Subject newSubject = new Subject(subject);
            book.getSubjects().add(newSubject);
        }

        return book;
    }
}
