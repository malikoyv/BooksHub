package com.malikoyv.core.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "summary_id", referencedColumnName = "id")
    private Summary summary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "counts_id", referencedColumnName = "id")
    private Counts counts;

    public Rating(Book book, Summary summary, Counts dbCounts) {
        this.book = book;
        this.summary = summary;
        this.counts = dbCounts;
    }
}
