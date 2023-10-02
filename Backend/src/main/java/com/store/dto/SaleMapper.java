package com.store.dto;

import com.store.models.Sale;

import java.time.LocalDateTime;

public class SaleMapper {
    public static SaleDTO toDTO(Sale domainSale) {
        String customerName = domainSale.getCustomer().getName();
        String movieTitle = domainSale.getItems().get(0).getMovieCopy().getMovie().getMovieTitle();
        int quantityBought = domainSale.getItems().get(0).getCopiesSold();
        double saleTotalPrice = domainSale.getRevenue();
        LocalDateTime saleDate = domainSale.getSaleDate();

        return new SaleDTO(customerName, movieTitle,quantityBought, saleTotalPrice, saleDate);
    }
}

