package com.malikoyv.client;

public interface IBooksClientUriBuilderProvider {
    String buildSearchUrl(String query, int page);

    String buildAuthorSearchUrl(String authorName);

    String buildEditionDetailsUrl(String workKey);

    String buildRatingsUrl(String bookKey);
}
