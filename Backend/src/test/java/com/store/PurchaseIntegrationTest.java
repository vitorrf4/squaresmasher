package com.store;

import com.store.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.TestComponent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@TestComponent
public class PurchaseIntegrationTest {
    @ParameterizedTest(name = "Sold {0} copies ")
    @ValueSource(ints = {1, 4, 5})
    @DisplayName("Purchase Integration - Success")
    public void whenMakePurchase_thenSuccess(int copiesSold) {
        Movie movie1 = new Movie("test movie", Date.valueOf("2000-08-01"), List.of("Horror"));
        MovieCopy copy1 = new MovieCopy(movie1, 5, 20);
        StoreStock stock1 = new StoreStock();
        stock1.addMovieToStock(copy1);

        Store store1 = new Store("test store", stock1);

        List<SaleItem> items = new ArrayList<>();
        items.add(new SaleItem(copy1, copiesSold));

        Customer customer = new Customer("david");
        boolean expectedPurchaseBool = customer.makePurchase(items, store1);

        assertThat(expectedPurchaseBool).isTrue();
        assertThat(copy1.getCopiesAmount()).isEqualTo(5 - copiesSold);
        assertThat(stock1.getTotalCopies()).isEqualTo(5 - copiesSold);
        assertThat(store1.getSales().size()).isEqualTo(1);
        assertThat(store1.getTotalRevenue()).isEqualTo(copiesSold * 20);
    }

    @ParameterizedTest(name = "Did not sell {0} copies ")
    @ValueSource(ints = {0, -5, 6, Integer.MAX_VALUE})
    @DisplayName("Purchase with invalid parameters - Failure")
    public void whenMakePurchase_givenNotEnoughCopies_thenFailure(int copiesSold) {
        Movie movie1 = new Movie("test movie", Date.valueOf("2000-08-01"), List.of("Horror"));
        MovieCopy copy1 = new MovieCopy(movie1, 5, 20);
        StoreStock stock1 = new StoreStock();
        stock1.addMovieToStock(copy1);

        Store store1 = new Store("test store", stock1);

        List<SaleItem> items = new ArrayList<>();
        items.add(new SaleItem(copy1, copiesSold));

        Customer customer = new Customer("david");
        boolean expectedPurchaseBool = customer.makePurchase(items, store1);

        assertThat(expectedPurchaseBool).isFalse();
        assertThat(copy1.getCopiesAmount()).isEqualTo(5);
        assertThat(stock1.getTotalCopies()).isEqualTo(0);
        assertThat(store1.getSales().size()).isEqualTo(0);
        assertThat(store1.getTotalRevenue()).isEqualTo(0);

    }
}
