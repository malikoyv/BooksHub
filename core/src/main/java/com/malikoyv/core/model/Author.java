package com.malikoyv.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "death_date")
    private LocalDate deathDate;

    private String biography;

    @Column(name = "author_key", unique = true, nullable = false)
    private String key;

    @ElementCollection
    @CollectionTable(name = "author_alternate_names", joinColumns = @JoinColumn(name = "author_id"))
    @Column(name = "alternate_name")
    private List<String> alternateNames = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "author_top_subjects", joinColumns = @JoinColumn(name = "author_id"))
    @Column(name = "top_subject")
    private List<String> topSubjects = new ArrayList<>();

    private String topWork;
    private int workCount;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();

    public Author(String author1) {
        name = author1;
    }
}