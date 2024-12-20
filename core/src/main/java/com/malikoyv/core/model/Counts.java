package com.malikoyv.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Counts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "one_star")
    private int oneStar;

    @Column(name = "two_stars")
    private int twoStars;

    @Column(name = "three_stars")
    private int threeStars;

    @Column(name = "four_stars")
    private int fourStars;

    @Column(name = "five_stars")
    private int fiveStars;
}
