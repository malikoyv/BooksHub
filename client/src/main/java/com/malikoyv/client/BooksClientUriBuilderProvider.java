package com.malikoyv.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

public class BooksClientUriBuilderProvider implements IBooksClientUriBuilderProvider {
    @Value("${openlibrary.api.host}")
    private String host;

    private UriComponentsBuilder builder(){
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(host);
    }

    @Override
    public String buildSearchUrl(String query, int page) {
        return builder()
                .pathSegment("search.json")
                .queryParam("q", query)
                .queryParam("page", page)
                .toUriString();
    }

    @Override
    public String buildAuthorSearchUrl(String authorName) {
        return builder()
                .pathSegment("search")
                .pathSegment("authors.json")
                .queryParam("q", authorName)
                .toUriString();
    }

    @Override
    public String buildEditionDetailsUrl(String workKey) {
        return builder()
                .pathSegment("works")
                .pathSegment(workKey + ".json")
                .toUriString();
    }
}
