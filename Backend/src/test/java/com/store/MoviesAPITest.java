package com.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.store.models.MovieCopy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootTest
public class MoviesAPITest {
    @Value("${tmdb.api.key}")
    public String apiKey;

    @Test
    public void whenCallApi_thenSuccess() throws JsonProcessingException, ExecutionException, InterruptedException {
        int randomPage = (int)(Math.random() * 99) + 1;
        URI uri = URI.create("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US" +
                "&page=" + randomPage +
                "&sort_by=vote_count.desc");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + apiKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        var responseTask = HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());
        var response = responseTask.get();

        ObjectMapper mapper = new ObjectMapper();

        JsonNode resultsNode = mapper.readTree(response.body());
        ArrayNode arrayNode = (ArrayNode) resultsNode.get("results");

        TypeReference<List<MovieCopy>> movieReference = new TypeReference<>() {};

//        List<MovieCopy> movies = mapper.readValue(arrayNode.toString(), movieReference);

//        movies.forEach(m -> {
//            System.out.println(m);
//        });

    }
}
