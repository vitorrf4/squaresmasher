package com.store.dto;

import java.time.Year;

public record MovieDTO(
    String movieTitle,
    Year releaseYear,
    double unitPrice,
    int copiesAmount,
    String posterUrl
) {
    @Override
    public String toString() {
        return "MovieDTO{" +
                "movieTitle='" + movieTitle + '\'' +
                ", releaseYear=" + releaseYear +
                ", unitPrice=" + unitPrice +
                ", copiesAmount=" + copiesAmount +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}
