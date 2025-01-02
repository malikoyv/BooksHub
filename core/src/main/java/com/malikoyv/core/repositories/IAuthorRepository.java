package com.malikoyv.core.repositories;

import com.malikoyv.core.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByKey(String key);
    Optional<Author> findByName(String name);

    List<Author> findByKeyIn(List<String> authors);

    Optional<Author> findAuthorByName(String authorName);
}
