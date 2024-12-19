package com.malikoyv.client.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BookDto(@JsonProperty("title") String title,
                      @JsonProperty("key") String key,
                      @JsonProperty("first_publish_year") Integer firstPublishYear,
                      @JsonProperty("author_name") List<String> authorNames) {
}
