package com.malikoyv.core.repositories;

import com.malikoyv.core.model.Edition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEditionRepository extends JpaRepository<Edition, Long> {
}
