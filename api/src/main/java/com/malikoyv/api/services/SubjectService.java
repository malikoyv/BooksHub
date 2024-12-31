package com.malikoyv.api.services;

import com.malikoyv.core.model.Subject;
import com.malikoyv.core.repositories.ICatalogData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final ICatalogData db;

    public SubjectService(ICatalogData db) {
        this.db = db;
    }

    public long saveSubject(String name) {
        Subject subject = new Subject();
        subject.setName(name);
        db.getSubjects().save(subject);
        return subject.getId();
    }

    public List<Subject> getAllSubjects() {
        return db.getSubjects().findAll();
    }

    public void deleteSubject(Long id) {
        db.getSubjects().deleteById(id);
    }
}
