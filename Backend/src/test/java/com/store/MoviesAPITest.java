package com.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MoviesAPITest {
    @Value("${tmdb.api.key}")
    public String apiKey;

    @Test
    public void whenCallApi_thenSuccess() {

    }
}
