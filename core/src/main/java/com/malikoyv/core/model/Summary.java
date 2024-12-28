package com.malikoyv.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double average;
    private int count;

    @Column(name = "sortable_average")
    private double sortableAverage;

    public Summary(double average, int count, double sortableAverage) {
        this.average = average;
        this.count = count;
        this.sortableAverage = sortableAverage;
    }
}
