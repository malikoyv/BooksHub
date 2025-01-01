package com.malikoyv.client.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.malikoyv.core.model.Rating;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookDto(
        String key,
        String title,
        @JsonProperty("author_name") List<String> authors,
        @JsonProperty("first_publish_year") String firstPublishDate,
        @JsonProperty("subject") List<String> subjects,
        RatingDto rating
) {
}