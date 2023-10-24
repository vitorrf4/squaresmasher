package com.store.models;

public record AuthenticatedUser(
    String userName,
    String storeName,
    String accessToken
) { }
