package com.malikoyv.client.contract;

import java.util.List;

public record BookPagedResultDto(int start,
                                 int numFound,
                                 List<BookDto> docs) {
}
