package com.malikoyv.core.repositories;

import com.malikoyv.core.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT r FROM Rating r WHERE r.book.id = :bookId")
    Optional<Rating> findByBookId(Long bookId);
}
