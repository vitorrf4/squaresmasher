package com.store;

import com.store.dto.SaleDTO;
import com.store.dto.SaleMapper;
import com.store.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestComponent
public class SaleDTOTest {
    private Sale sale;
    private SaleItem item;
    private Customer customer;
    private Movie copy;

    @BeforeEach
    public void setup() {
        copy = new Movie("test movie", 3, 10);

        item = new SaleItem(copy, 2);

        customer = new Customer("test");
        sale = new Sale(List.of(item), customer);
    }

    @Test
    @DisplayName("Sale to SaleDTO - Success")
    public void whenToDto_givenValidSaleObject_thenSuccess() {
        SaleDTO dto = SaleMapper.toDTO(sale);

        assertThat(dto.customerName()).isEqualTo(customer.getName());
        assertThat(dto.movieTitle()).isEqualTo(copy.getMovieTitle());
        assertThat(dto.quantityBought()).isEqualTo(item.getCopiesSold());
        assertThat(dto.saleTotalPrice()).isEqualTo(item.getItemTotalPrice());
        assertThat(dto.saleDateTime()).isEqualTo(sale.getSaleDate());
    }

}
