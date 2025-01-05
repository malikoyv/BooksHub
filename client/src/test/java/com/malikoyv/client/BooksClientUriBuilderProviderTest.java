package com.malikoyv.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BooksClientUriBuilderProviderTest {
    private BooksClientUriBuilderProvider provider;

    @BeforeEach
    public void setUp() {
        provider = new BooksClientUriBuilderProvider();
    }

    @Test
    public void testBuildSearchUrl() {
        String query = "test";
        int page = 1;
        String expectedUrl = "https://openlibrary.org/search.json?q=test&page=1";

        String actualUrl = provider.buildSearchUrl(query, page);

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void testBuildAuthorSearchUrl() {
        String authorName = "testAuthor";
        String expectedUrl = "https://openlibrary.org/search/authors.json?q=testAuthor";

        String actualUrl = provider.buildAuthorSearchUrl(authorName);

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void testBuildRatingsUrl() {
        String bookKey = "testBookKey";
        String expectedUrl = "https://openlibrary.org/works/testBookKey/ratings.json";

        String actualUrl = provider.buildRatingsUrl(bookKey);

        assertEquals(expectedUrl, actualUrl);
    }
}
