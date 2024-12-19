package com.malikoyv.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Edition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key; // e.g., /books/OL37239326M
    private String title;
    private String language;

    private LocalDate firstPublishDate;

    private String coverEdition;

    @ElementCollection
    private List<String> subjectPlaces = new ArrayList<>();

    @ElementCollection
    private List<String> subjectPeople = new ArrayList<>();

    @ElementCollection
    private List<String> subjects = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
