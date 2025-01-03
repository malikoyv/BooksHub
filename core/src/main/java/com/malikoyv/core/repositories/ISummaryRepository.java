package com.malikoyv.core.repositories;

import com.malikoyv.core.model.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISummaryRepository extends JpaRepository<Summary, Long>{
}
