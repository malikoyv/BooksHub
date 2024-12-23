package com.malikoyv.api.contract;

import java.time.LocalDate;

public record BookDto (long id,
                       String title,
                       LocalDate publishDate,
                       String description,
                       int pageCount,
                       String language) {}
