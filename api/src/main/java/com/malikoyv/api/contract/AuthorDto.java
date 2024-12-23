package com.malikoyv.api.contract;

import java.time.LocalDate;

public record AuthorDto (long id,
                         String name,
                         LocalDate birthday,
                         String biography){
}
