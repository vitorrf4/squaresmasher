package com.store.services;

import com.store.models.Customer;
import com.store.models.Movie;
import com.store.repos.CustomerRepository;
import com.store.repos.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository repo;

    @Autowired
    public MovieService(MovieRepository repo) {
        this.repo = repo;
    }

    public List<Movie> getAllMovies() {
        return repo.findAll();
    }

    public Movie getMovie(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Movie registerMovie(Movie customer) {
        if (customer == null) return null;
        return repo.save(customer);
    }

    public Movie updateMovie(Movie customer) {
        if (customer == null) return null;
        if (!repo.existsById(customer.getId())) return null;
        return repo.save(customer);
    }

    public boolean deleteMovie(Long id) {
        Optional<Movie> movie = repo.findById(id);
        if (movie.isEmpty()) return false;
        repo.delete(movie.get());
        return true;
    }
}
