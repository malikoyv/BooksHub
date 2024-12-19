package com.malikoyv.client.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;
import java.util.List;

public record EditionDto(String key,
                         String title,
                         String language,
                         @JsonProperty("first_publish_date")
                         String firstPublishDate,
                         @JsonProperty("cover_edition") CoverEditionDto coverEdition,
                         @JsonProperty("subject_places") List<String> subjectPlaces,
                         @JsonProperty("subject_people") List<String> subjectPeople,
                         List<String> subjects) {

    public record CoverEditionDto(String key) {}
}
