package com.malikoyv.client.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BookDto(
        String key,
        String title,
        @JsonProperty("author_name") List<String> authors,
        @JsonProperty("first_publish_year") String firstPublishDate,
        @JsonProperty("subjects") List<String> subjects
) {
}