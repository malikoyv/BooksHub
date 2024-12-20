package com.malikoyv.core.repositories;

import com.malikoyv.core.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, Long> {
}