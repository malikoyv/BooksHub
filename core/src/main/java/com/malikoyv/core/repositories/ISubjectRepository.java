package com.malikoyv.core.repositories;

import com.malikoyv.core.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByName(String name);
}
