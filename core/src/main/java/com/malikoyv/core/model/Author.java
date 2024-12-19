package com.malikoyv.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthday;

    private LocalDate deathday;

    private String key; // from the API

    @ElementCollection
    private List<String> alternateNames = new ArrayList<>();

    @ElementCollection
    private List<String> topSubjects = new ArrayList<>();

    private String topWork;
    private int workCount;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();
}