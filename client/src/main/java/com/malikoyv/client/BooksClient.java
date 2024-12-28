package com.malikoyv.client;

import com.malikoyv.client.contract.AuthorSearchResponse;
import com.malikoyv.client.contract.EditionDto;
import com.malikoyv.client.contract.BookPagedResultDto;
import com.malikoyv.client.contract.RatingDto;
import org.springframework.web.client.RestTemplate;

public class BooksClient implements IBooksClient {
    private final RestTemplate restTemplate;
    private final IBooksClientUriBuilderProvider provider;

    public BooksClient(IBooksClientUriBuilderProvider provider, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.provider = provider;
    }

    @Override
    public BookPagedResultDto searchBooks(String query, int page) {
        String url = provider.buildSearchUrl(query, page);
        return restTemplate.getForEntity(url, BookPagedResultDto.class).getBody();
    }

    @Override
    public AuthorSearchResponse searchAuthors(String authorName) {
        String url = provider.buildAuthorSearchUrl(authorName);
        return restTemplate.getForEntity(url, AuthorSearchResponse.class).getBody();
    }

    @Override
    public EditionDto getEditionDetails(String workKey) {
        String url = provider.buildEditionDetailsUrl(workKey);
        return restTemplate.getForEntity(url, EditionDto.class).getBody();
    }

    @Override
    public RatingDto getRatings(String bookKey) {
        String url = provider.buildRatingsUrl(bookKey);
        return restTemplate.getForEntity(url, RatingDto.class).getBody();
    }
}