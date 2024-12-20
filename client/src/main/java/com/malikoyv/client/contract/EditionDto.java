package com.malikoyv.client.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public record EditionDto(
        String key,
        String title,
        String language,
        @JsonProperty("first_publish_date") String firstPublishDate,
        List<Integer> covers,
        @JsonProperty("subject_places") List<String> subjectPlaces,
        @JsonProperty("subject_people") List<String> subjectPeople,
        List<String> subjects,
        @JsonProperty("subject_times") List<String> subjectTimes) {
}