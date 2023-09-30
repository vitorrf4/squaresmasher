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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestComponent
public class SaleDTOTest {
    private SaleDTO dto;
    private Sale sale;
    private Store store;
    private MovieCopy copy;
    private Movie movie;
    private StoreStock stock;
    private List<SaleItem> items;
    private Customer customer;

    @BeforeEach
    public void setup() {
        movie = new Movie("test title", Date.valueOf(LocalDate.now()), List.of("genre 1"));
        copy = new MovieCopy(movie, 3, 10);
        stock = new StoreStock(List.of(copy));
        store = new Store("test store", stock);
        SaleItem item = new SaleItem(copy, 2);
        items = new ArrayList<>();
        items.add(item);
        customer = new Customer("test");
        sale = new Sale(items, customer);
    }

    @Test
    @DisplayName("Sale to SaleDTO - Success")
    public void whenToDto_thenSuccess() {
        dto = SaleMapper.toDTO(sale);
        System.out.println(dto);

        assertThat(dto.getCustomerName()).isEqualTo(customer.getName());
    }

}
