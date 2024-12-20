package com.malikoyv.client.contract;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookPagedResultDto(
                        int numFound,
                        int start,
                        boolean numFoundExact,
                        @JsonProperty("docs") List<BookDto> books
) {
}
