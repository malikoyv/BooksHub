package com.malikoyv.core.repositories;

import com.malikoyv.core.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthorRepository extends JpaRepository<Author, Long> {
}
