package com.store.dto;

import com.store.models.Movie;
import com.store.models.Store;
import java.util.List;

public class StoreMapper {
    public static StoreDTO toDTO(Store store) {
        String name = store.getName();
        double totalRevenue = store.getTotalRevenue();
        int copiesTotal = store.getStock().getCopiesTotal();
        List<Movie> moviesInStock = store.getStock().getAllCopies();

        return new StoreDTO(name, totalRevenue, copiesTotal, moviesInStock);
    }
}

