package com.malikoyv.client.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AuthorSearchResponse(
            int numFound,
            int start,
            boolean numFoundExact,
            @JsonProperty("docs") List<AuthorDto> authors
){}
