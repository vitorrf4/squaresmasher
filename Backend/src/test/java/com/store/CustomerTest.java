package com.store;

import com.store.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestComponent;
import java.security.InvalidParameterException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestComponent
public class CustomerTest {
    @Test
    @DisplayName("Customer Creation - Exception")
    public void whenCreateUser_givenNullName_thenException() {
        assertThrows(InvalidParameterException.class, () -> new Customer(null));
    }

    @Test
    @DisplayName("Customer Set Name - Exception")
    public void whenSetNameToNull_thenException() {
        Customer customer = new Customer();
        assertThrows(InvalidParameterException.class, () -> customer.setName(null));
    }

    @Test
    @DisplayName("Customer Purchase With Null Parameters - Fail")
    public void whenMakePurhcase_givenNullParameters_thenFail() {
        Customer customer = new Customer("test");
        boolean expectedPurchaseBool = customer.makePurchase(null, null);
        assertThat(expectedPurchaseBool).isFalse();
    }

    @Test
    @DisplayName("Customer Purchase - Sucessful")
    public void whenMakePurhcase_thenTrue() {
        Customer customer = new Customer("test");
        Movie movie1 = new Movie("House of Leaves", Date.valueOf("2000-08-01"),List.of("Horror"));
        MovieCopy copy1 = new MovieCopy(movie1, 5, 20);
        StoreStock stock1 = new StoreStock();
        stock1.addMovieToStock(copy1);

        Store store = new Store("test store", stock1);

        List<SaleItem> items = new ArrayList<>();
        items.add(new SaleItem(copy1, 5));

        boolean expectedPurchaseBool = customer.makePurchase(items, store);
        assertThat(expectedPurchaseBool).isTrue();
    }

}
