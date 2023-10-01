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

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class RandomSaleIntegrationTest {
    @Autowired private RandomSaleController controller;
    @Autowired private UserRepository userRepository;

    List<MovieCopy> copies;
    StoreStock stock;
    Store store;
    User user;

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

        user = new User("test user", "test password", store);

        user = userRepository.save(user);
    }

    @Test
    @DisplayName("Generate Purchase - Success")
    public void whenGenerateRandomPurhcase_thenSuccess() {

        ResponseEntity<SaleDTO> actualSale = controller.generateSale(user.getId());

        assertThat(actualSale.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualSale.getBody()).isInstanceOf(SaleDTO.class);
        assertThat(actualSale.getBody().saleDateTime().getSecond()).isEqualTo(LocalDateTime.now().getSecond());
        assertThat(actualSale.getBody().movieTitle())
                .isIn(
                copies.get(0).getMovie().getMovieTitle(),
                copies.get(1).getMovie().getMovieTitle(),
                copies.get(2).getMovie().getMovieTitle()
        );
    }

    @AfterEach
    public void removeTestStoreFromDb() {
        userRepository.deleteById(user.getId());
        System.out.println("Test user " + user.getId() + " removed");
    }
}
