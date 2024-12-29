package com.malikoyv.client.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AuthorDto(
        String name,
        String key,
        @JsonProperty("alternate_names") List<String> alternateNames,
        @JsonProperty("birth_date") String birthDate,
        @JsonProperty("death_date") String deathDate,
        @JsonProperty("top_subjects") List<String> topSubjects,
        @JsonProperty("top_work") String topWork,
        @JsonProperty("work_count") Integer workCount
) {
}