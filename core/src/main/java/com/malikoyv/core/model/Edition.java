package com.malikoyv.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Edition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "edition_key")
    private String key; // e.g., /books/OL37239326M
    private String title;
    private String language;

    @Temporal(TemporalType.DATE)
    @Column(name = "first_publish_date")
    private LocalDate firstPublishDate;

    @ElementCollection
    @CollectionTable(name = "edition_covers", joinColumns = @JoinColumn(name = "edition_id"))
    @Column(name = "cover_id")
    private List<Integer> covers = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "edition_subject_places", joinColumns = @JoinColumn(name = "edition_id"))
    @Column(name = "subject_place")
    private List<String> subjectPlaces = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "edition_subject_people", joinColumns = @JoinColumn(name = "edition_id"))
    @Column(name = "subject_person")
    private List<String> subjectPeople = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "edition_subjects", joinColumns = @JoinColumn(name = "edition_id"))
    @Column(name = "subject")
    private List<String> subjects = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "edition_subject_times", joinColumns = @JoinColumn(name = "edition_id"))
    @Column(name = "subject_time")
    private List<String> subjectTimes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}