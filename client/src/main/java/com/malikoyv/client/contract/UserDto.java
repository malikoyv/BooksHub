package com.malikoyv.client.contract;

public record UserDto(
        long id,
        String username,
        String email
) {
}
