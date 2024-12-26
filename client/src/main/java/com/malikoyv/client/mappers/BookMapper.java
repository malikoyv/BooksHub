package com.malikoyv.client.mappers;

import com.malikoyv.client.contract.BookDto;
import com.malikoyv.core.model.Book;
import org.springframework.stereotype.Component;

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
        return book;
    }
}
