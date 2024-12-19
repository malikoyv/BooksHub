package com.malikoyv.client.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record AuthorDto(
        String name,
        String key,
        @JsonProperty("birth_date") String birthDate,
        @JsonProperty("death_date") String deathDate,
        @JsonProperty("top_work") String topWork,
        @JsonProperty("work_count") int workCount
) {
}