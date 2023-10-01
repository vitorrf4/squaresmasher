package com.store;

import com.store.dto.SaleDTO;
import com.store.dto.SaleMapper;
import com.store.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestComponent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestComponent
public class SaleDTOTest {
    private Sale sale;
    private Movie movie;
    private SaleItem item;
    private Customer customer;

    @BeforeEach
    public void setup() {
        movie = new Movie("test title", Date.valueOf(LocalDate.now()), List.of("genre 1"));
        MovieCopy copy = new MovieCopy(movie, 3, 10);

        item = new SaleItem(copy, 2);

        customer = new Customer("test");
        sale = new Sale(List.of(item), customer);
    }

    @Test
    @DisplayName("Sale to SaleDTO - Success")
    public void whenToDto_thenSuccess() {
        SaleDTO dto = SaleMapper.toDTO(sale);

        assertThat(dto.customerName()).isEqualTo(customer.getName());
        assertThat(dto.movieTitle()).isEqualTo(movie.getMovieTitle());
        assertThat(dto.quantityBought()).isEqualTo(item.getCopiesSold());
        assertThat(dto.saleTotalPrice()).isEqualTo(item.getItemTotalPrice());
        assertThat(dto.saleDateTime()).isEqualTo(sale.getSaleDate());
    }

}
