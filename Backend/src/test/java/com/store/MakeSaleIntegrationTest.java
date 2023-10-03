package com.store;

import com.store.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.TestComponent;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@TestComponent
public class MakeSaleIntegrationTest {
    private MovieCopy movieCopy;
    private StoreStock stock;
    private List<SaleItem> itemsBought;
    private Store store;
    private Customer customer;

    @BeforeEach
    public void setup() {
        movieCopy = new MovieCopy("test movie", 5, 20);
        stock = new StoreStock();
        stock.addMovieToStock(movieCopy);

        store = new Store("test store", stock);

        itemsBought = new ArrayList<>();

        customer = new Customer("test customer");
    }

    @ParameterizedTest(name = "Sold {0} copies ")
    @ValueSource(ints = {1, 4, 5})
    @DisplayName("Valid purchase - Success")
    public void whenMakeSale_thenSuccess(int copiesBought) {
        itemsBought.add(new SaleItem(movieCopy, copiesBought));

        Sale actualSale = store.makeSale(itemsBought, customer);
        double expectedSaleRevenue = store.getSales().get(0).getRevenue();

        assertThat(actualSale).isInstanceOf(Sale.class);
        assertThat(movieCopy.getCopiesAmount()).isEqualTo(5 - copiesBought);
        assertThat(expectedSaleRevenue).isEqualTo(copiesBought * movieCopy.getPrice());
        assertThat(stock.getTotalCopies()).isEqualTo(5 - copiesBought);
        assertThat(store.getSales().size()).isEqualTo(1);
        assertThat(store.getTotalRevenue()).isEqualTo(copiesBought * 20);
    }

    @ParameterizedTest(name = "Did not sell {0} copies ")
    @ValueSource(ints = {0, -5, 6})
    @DisplayName("Purchase with invalid parameters - Failure")
    public void whenMakeSale_givenInvalidCopiesAmount_thenFailure(int copiesBought) {
        itemsBought.add(new SaleItem(movieCopy, copiesBought));

        Sale actualPurchase = store.makeSale(itemsBought, customer);

        assertThat(actualPurchase).isNull();
        assertThat(movieCopy.getCopiesAmount()).isEqualTo(5);
        assertThat(stock.getTotalCopies()).isEqualTo(5);
        assertThat(store.getSales().size()).isEqualTo(0);
        assertThat(store.getTotalRevenue()).isEqualTo(0);
    }
}
