package com.malikoyv.core.repositories;

import com.malikoyv.core.model.Edition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEditionRepository extends JpaRepository<Edition, Long> {
}
