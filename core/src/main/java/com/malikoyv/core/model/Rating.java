package com.malikoyv.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Book book;

    @OneToOne(cascade = CascadeType.ALL)
    private Summary summary;

    @OneToOne(cascade = CascadeType.ALL)
    private Counts counts;
}
