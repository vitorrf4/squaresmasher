package com.store.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.store.models.Movie;
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
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    public List<Movie> searchMovie(String query) {
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

            List<Movie> copies = parseResponseToMovieList(response.body());

            return copies;
        } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
            return null;
        }

    }

    public List<Movie> parseResponseToMovieList(String response) throws JsonProcessingException {
        ArrayNode arrayNode = (ArrayNode)mapper.readValue(response, JsonNode.class).get("results");

        TypeReference<List<Movie>> movieReference = new TypeReference<>() {};

        return mapper.readValue(arrayNode.toString(), movieReference);
    }
}
