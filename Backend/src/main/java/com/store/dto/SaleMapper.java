package com.store.dto;

import com.store.models.Movie;
import com.store.models.Sale;
import com.store.models.SaleItem;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleMapper {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static SaleDTO toDTO(Sale domainSale) {
        // John has bought 3 copies of Brick, and 2 copies of Raw | Sale Total: $20.00 | 11:22 - 28/09/2023

        String customerName = domainSale.getCustomer().getName();
        Map<String, Integer> movies = new HashMap<>();

        domainSale.getItems().forEach(item -> {
            String movieTitle = item.getMovieCopy().getMovie().getMovieTitle();
            movies.put(movieTitle, item.getCopiesSold());
        });

        double saleTotalPrice = domainSale.getRevenue();
        LocalDate saleDate = domainSale.getSaleDate();

        return new SaleDTO(customerName, movies, saleTotalPrice, saleDate);
    }
}

