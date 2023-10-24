package com.store.models;

public record AuthenticatedUser(
    Long id,
    String userName,
    String storeName,
    String accessToken
) { }
