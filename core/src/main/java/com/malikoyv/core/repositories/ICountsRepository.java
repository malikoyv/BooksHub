package com.malikoyv.core.repositories;

import com.malikoyv.core.model.Counts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICountsRepository extends JpaRepository<Counts, Long> {
}
