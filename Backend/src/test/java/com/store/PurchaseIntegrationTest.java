package com.store;

import com.store.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestComponent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@TestComponent
public class PurchaseIntegrationTest {

    @Test
    @DisplayName("Purchase Integration - Success")
    public void whenMakePurchase_thenSuccess() {
        Movie movie1 = new Movie("test movie", Date.valueOf("2000-08-01"), List.of("Horror"));
        MovieCopy copy1 = new MovieCopy(movie1, 5, 20);
        StoreStock stock1 = new StoreStock();
        stock1.addMovieToStock(copy1);

        Store store1 = new Store("test store", stock1);

        List<SaleItem> items = new ArrayList<>();
        items.add(new SaleItem(copy1, 5));

        Customer customer = new Customer("david");
        boolean expectedPurchaseBool = customer.makePurchase(items, store1);

        assertThat(expectedPurchaseBool).isTrue();
        assertThat(copy1.getCopiesAmount()).isEqualTo(0);
        assertThat(stock1.getTotalCopies()).isEqualTo(0);
        assertThat(store1.getSales().size()).isEqualTo(1);
        assertThat(store1.getTotalRevenue()).isEqualTo(100);
    }
}
