package com.malikoyv.core.repositories;

import com.malikoyv.core.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepository extends JpaRepository<Book, Long> {
}
