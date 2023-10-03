package com.store.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.store.models.MovieCopy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MoviesAPIService {
    @Value("${tmdb.api.key}")
    private String apiKey;
    private ObjectMapper mapper = new ObjectMapper();

    public void getMovies() {

    }

    public String searchMovie(String query) {
        URI uri = URI.create("https://api.themoviedb.org/3/search/movie?&include_adult=false&language=en-US&page=1" +
                "&query=" + query);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + apiKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        var responseTask = HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            var response = responseTask.get();
            JsonNode node = mapper.readTree(response.body()).get("results");
            System.out.println("Node:" + node);
            System.out.println("Node text value:" + node.toString());
            return node.toString();
        } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
