package com.malikoyv.client.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RatingDto(
        @JsonProperty("summary") SummaryDto summaryDto,
        @JsonProperty("counts") CountsDto countsDto
) {
    public record SummaryDto(
            double average,
            int count,
            @JsonProperty("sortable") double sortableAverage
    ) {}

    public record CountsDto(
            @JsonProperty("1") int oneStar,
            @JsonProperty("2") int twoStars,
            @JsonProperty("3") int threeStars,
            @JsonProperty("4") int fourStars,
            @JsonProperty("5") int fiveStars
    ) {}
}