package com.store.dto;

import com.store.models.Movie;
import java.util.List;

public record StoreDTO(
    String name,
    double totalRevenue,
    int copiesTotal,
    List<Movie> moviesInStock
) { }
