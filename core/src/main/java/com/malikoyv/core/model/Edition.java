package com.malikoyv.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
