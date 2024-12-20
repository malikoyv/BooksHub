package com.malikoyv.client;

import com.malikoyv.client.contract.AuthorSearchResponse;
import com.malikoyv.client.contract.EditionDto;
import com.malikoyv.client.contract.BookPagedResultDto;
import com.malikoyv.client.contract.RatingsDto;

public interface IBooksClient {
    BookPagedResultDto searchBooks(String query, int page);

    AuthorSearchResponse searchAuthors(String authorName);

    EditionDto getEditionDetails(String workKey);

    RatingsDto getRatings(String bookKey);
}
