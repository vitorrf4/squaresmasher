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

    public List<Movie> getAllCustomers() {
        return repo.findAll();
    }

    public Movie getCustomer(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Movie createCustomer(Movie customer) {
        if (customer == null) return null;
        return repo.save(customer);
    }

    public Movie updateCustomer(Movie customer) {
        if (customer == null) return null;
        if (!repo.existsById(customer.getId())) return null;
        return repo.save(customer);
    }

    public boolean deleteCustomer(Long id) {
        Optional<Movie> movie = repo.findById(id);
        if (movie.isEmpty()) return false;
        repo.delete(movie.get());
        return true;
    }
}
