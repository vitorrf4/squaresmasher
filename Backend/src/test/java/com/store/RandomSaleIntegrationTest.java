package com.store;

import com.store.controllers.RandomSaleController;
import com.store.dto.SaleDTO;
import com.store.models.*;
import com.store.repos.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RandomSaleIntegrationTest {
    @Autowired private RandomSaleController controller;
    @Autowired private UserRepository userRepository;
    private List<Movie> copies;
    private User user;

    @Autowired
    public RandomSaleIntegrationTest(RandomSaleController controller) {
        this.controller = controller;
    }

    @BeforeEach
    public void setup() {
        copies = new ArrayList<>(List.of(
                new Movie("movie1", 2, Year.of(2019)),
                new Movie("movie2", 5, Year.of(1995)),
                new Movie("movie3", 1, Year.of(1963))
        ));

        StoreStock stock = new StoreStock(copies);
        Store store = new Store("test store", stock);

        user = new User("test user", "test password", store);

        user = userRepository.save(user);
    }

    @Test
    @DisplayName("Generate Purchase - Success")
    public void whenGenerateRandomPurhcase_thenSuccess() {
        ResponseEntity<?> saleResponse = controller.generateSale(user.getId());

        SaleDTO actualSale = (SaleDTO) saleResponse.getBody();

        assertThat(saleResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualSale).isInstanceOf(SaleDTO.class);
        assertThat(actualSale.movieTitle()).isIn(
            copies.get(0).getTitle(),
            copies.get(1).getTitle(),
            copies.get(2).getTitle()
        );
    }

    @Test
    @DisplayName("No Movie Copies - NotFound")
    public void whenGenerateRandomPurchase_givenNoMovieCopies_thenNotFound() {
        int copiesInStock = 0;
        for (Movie copy : copies) {
            copy.takeCopies(copy.getCopiesAmount());
            copiesInStock += copy.getCopiesAmount();
        }

        userRepository.save(user);

        ResponseEntity<?> saleResponse = controller.generateSale(user.getId());

        assertThat(saleResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(saleResponse.getBody().toString()).isEqualTo("No movie copies in stock");
        assertThat(copiesInStock).isEqualTo(0);
    }

    @Test
    @DisplayName("Null Store - BadRequest")
    public void whenGenerateRandomPurchase_givenNullStore_thenBadRequest() {
        user.setStore(null);
        userRepository.save(user);

        ResponseEntity<?> saleResponse = controller.generateSale(user.getId());

        assertThat(saleResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(saleResponse.getBody().toString()).isEqualTo("Invalid user store");
    }

    @AfterEach
    public void removeTestUserFromDb() {
        userRepository.deleteById(user.getId());
        System.out.println("Test user " + user.getId() + " removed");
    }
}
