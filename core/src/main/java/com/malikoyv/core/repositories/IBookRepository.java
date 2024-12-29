package com.malikoyv.core.repositories;

import com.malikoyv.core.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {
    Book findByKey(String key);

    void deleteByKey(String key);
}
