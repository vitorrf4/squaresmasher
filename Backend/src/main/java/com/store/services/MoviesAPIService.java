package com.store.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.store.models.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Service
public class MoviesAPIService {
    @Value("${tmdb.api.key}")
    private String apiKey;
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public HttpResponse<String> callQueryOnApi(String query) {
        URI uri = URI.create("https://api.themoviedb.org/3/search/movie?&include_adult=false&language=en-US&page=1" +
                "&query=" + URLEncoder.encode(query, StandardCharsets.UTF_8));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + apiKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        var responseTask = HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            return responseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.warning(e.toString());
            return null;
        }

    }

    public List<Movie> parseResponseToMovieList(String response) {
        ArrayNode arrayNode;
        try {
            arrayNode = (ArrayNode)mapper.readValue(response, JsonNode.class).get("results");
        } catch (JsonProcessingException e) {
            logger.warning(e.toString());
            return null;
        }

        List<Movie> movies = new ArrayList<>();

        arrayNode.forEach(node -> {
            Movie movie = createMovieWithJsonNode(node);
            if (movie != null) movies.add(movie);
        });

        return movies;
    }

    private Movie createMovieWithJsonNode(JsonNode node) {
        String title = node.get("title").textValue();

        if (node.get("release_date").textValue().isEmpty()
                || LocalDate.parse(node.get("release_date").textValue()).isAfter(LocalDate.now())) {
            return null;
        }

        Year releaseYear  = Year.parse(node.get("release_date").textValue().substring(0, 4));
        String posterUrl = setPosterUrl(node);

        return new Movie(title, 0, releaseYear, posterUrl);
    }

    private String setPosterUrl(JsonNode node) {
        if (node.get("poster_path").isNull()) {
            return "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/390px-No-Image-Placeholder.svg.png";
        }

        return "https://image.tmdb.org/t/p/w300/" + node.get("poster_path").textValue();

    }
}
