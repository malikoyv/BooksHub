package com.malikoyv.core.repositories;

import com.malikoyv.core.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.book.id = :bookId")
    List<Review> findByBookId(Long bookId);
    @Query("SELECT r FROM Review r WHERE r.user.id = :userId")
    List<Review> findByUserId(Long userId);
}
