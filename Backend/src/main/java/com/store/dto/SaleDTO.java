package com.store.dto;

import java.time.LocalDateTime;

public record SaleDTO (
        String customerName,
        String movieTitle,
        int quantityBought,
        double saleTotalPrice,
        LocalDateTime saleDateTime
) { }

