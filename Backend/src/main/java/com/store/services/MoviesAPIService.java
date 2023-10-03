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
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
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

            return parseResponseToMovieList(response.body());
        } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
            return null;
        }

    }

    public List<Movie> parseResponseToMovieList(String response) throws JsonProcessingException {
        ArrayNode arrayNode = (ArrayNode)mapper.readValue(response, JsonNode.class).get("results");

        List<Movie> movies = new ArrayList<>();

        arrayNode.forEach(node -> {
            Movie movie = createMovieWithJsonNode(node);
            if (movie != null) movies.add(movie);
        });

        return movies;
    }

    public Movie createMovieWithJsonNode(JsonNode node) {
        String title = node.get("title").textValue();

        if (node.get("release_date").textValue().isEmpty() ||
                LocalDate.parse(node.get("release_date").textValue()).isAfter(LocalDate.now())) {
            System.out.println(title + " has an invalid date or has not yet been released");
            return null;
        }

        Year releaseYear  = Year.parse(node.get("release_date").textValue().substring(0, 4));

        String posterUrl = node.get("poster_path").textValue();

        return new Movie(title, 0, releaseYear, posterUrl);
    }
}
