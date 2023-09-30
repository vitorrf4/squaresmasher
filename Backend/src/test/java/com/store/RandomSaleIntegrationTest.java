package com.store;

import com.store.controllers.RandomSaleController;
import com.store.models.*;
import com.store.repos.SaleRepository;
import com.store.repos.StoreRepository;
import com.store.services.RandomSaleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class RandomSaleIntegrationTest {
    @Autowired private RandomSaleController controller;
    @Autowired private RandomSaleService service;
    @Autowired private StoreRepository storeRepository;
    @Autowired private SaleRepository saleRepository;

    List<MovieCopy> copies;
    StoreStock stock;
    Store store;

    @Autowired
    public RandomSaleIntegrationTest(RandomSaleController controller) {
        this.controller = controller;
    }

    @BeforeEach
    public void setup() {
        Movie movie1 = new Movie("movie 1", Date.valueOf("2000-01-01"), List.of("genre 1"));
        Movie movie2 = new Movie("movie 2", Date.valueOf("2000-02-02"), List.of("genre 2"));
        Movie movie3 = new Movie("movie 3", Date.valueOf("2000-03-03"), List.of("genre 3"));

        copies = new ArrayList<>(List.of(
                new MovieCopy(movie1, 2, 10),
                new MovieCopy(movie2, 5, 30),
                new MovieCopy(movie3, 1, 30.5)
        ));

        stock = new StoreStock(copies);
        store = new Store("test store", stock);

        storeRepository.save(store);
    }

    @Test
    @DisplayName("Generate Purchase - Success")
    public void whenGenerateRandomPurhcase_thenSuccess() {
        User user = new User("test user", "test password", store);

        ResponseEntity<Sale> actualSale = controller.generateSale(user);

        double expectedRevenue = saleRepository.findById(actualSale.getBody().getId()).get().getRevenue();
        var expectedItemsSize = saleRepository.getSaleWithItems(actualSale.getBody().getId()).getItems().size();

        assertThat(actualSale.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualSale.getBody()).isInstanceOf(Sale.class);
        assertThat(actualSale.getBody().getRevenue()).isEqualTo(expectedRevenue);
        assertThat(actualSale.getBody().getItems().size()).isEqualTo(expectedItemsSize);
    }

    @AfterEach
    public void removeTestStoreFromDb() {
        storeRepository.deleteById(store.getId());
        System.out.println("Test store " + stock.getId() + " removed");
    }
}
