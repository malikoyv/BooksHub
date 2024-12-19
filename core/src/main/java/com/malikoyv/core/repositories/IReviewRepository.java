package com.malikoyv.core.repositories;

import com.malikoyv.core.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReviewRepository extends JpaRepository<Review, Long> {
}
