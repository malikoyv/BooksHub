package com.malikoyv.client.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RatingsDto(
        Summary summary,
        Counts counts
) {
    public record Summary(
            double average,
            int count,
            @JsonProperty("sortable") double sortableAverage
    ) {}

    public record Counts(
            @JsonProperty("1") int oneStar,
            @JsonProperty("2") int twoStars,
            @JsonProperty("3") int threeStars,
            @JsonProperty("4") int fourStars,
            @JsonProperty("5") int fiveStars
    ) {}
}