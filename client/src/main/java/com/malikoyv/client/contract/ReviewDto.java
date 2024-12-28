package com.malikoyv.client.contract;

public record ReviewDto(
        long id,
        String content,
        int rating
) {
}

