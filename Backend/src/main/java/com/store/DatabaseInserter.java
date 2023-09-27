package com.store;

import com.store.models.*;
import com.store.repos.*;
import com.store.services.*;
import com.store.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInserter implements CommandLineRunner {
    public final CustomerRepository customerRepo;
    public final MovieCopyRepository copyRepo;
    public final MovieRepository movieRepo;
    public final SaleItemRepository saleItemRepo;
    public final PurchaseService purchaseService;
    public final StoreRepository storeRepo;
    public final StoreStockRepository stockRepo;
    public final UserRepository userRepo;
    public final UserService userService;

    @Autowired
    public DatabaseInserter(CustomerRepository customerRepo, MovieCopyRepository copyRepo, MovieRepository movieRepo, SaleItemRepository saleItemRepo, PurchaseService purchaseService, StoreRepository storeRepo, StoreStockRepository stockRepo, UserRepository userRepo, UserService userService) {
        this.customerRepo = customerRepo;
        this.copyRepo = copyRepo;
        this.movieRepo = movieRepo;
        this.saleItemRepo = saleItemRepo;
        this.purchaseService = purchaseService;
        this.storeRepo = storeRepo;
        this.stockRepo = stockRepo;
        this.userRepo = userRepo;
        this.userService = userService;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(String... args) throws Exception {
        //TODO rollback failed initializations
        if (userRepo.existsById(1L)) return;

        User user1 = new User();
        user1.setName("test user");
        user1.setPassword("test password");

        Store store1 = new Store();
        store1.setName("test store");
        user1.setStore(store1);

        Movie movie1 = new Movie("House of Leaves", Date.valueOf("2000-08-01"),List.of("Horror"), 30);
        movie1.setMovieTitle("House of Leaves");
        movie1.setPrice(30);
        MovieCopy copy1 = new MovieCopy();

        copy1.setMovie(movie1);
        copy1.setCopiesAmount(3);

        StoreStock stock1 = new StoreStock();
        stock1.setCopies(List.of(copy1));
        store1.setStock(stock1);

        movieRepo.save(movie1);
        copyRepo.save(copy1);
        stockRepo.save(stock1);
        storeRepo.save(store1);
        userService.createUser(user1);
        SaleItem item1 = saleItemRepo.save(new SaleItem(movie1, 2));

        Customer john = customerRepo.save(new Customer("john"));

        List<SaleItem> items = new ArrayList<>();
        items.add(item1);
        purchaseService.makePurchase(items,john, store1);


        System.out.println("Initialized database");
    }
}
