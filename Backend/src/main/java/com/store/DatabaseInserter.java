package com.store;

import com.store.models.*;
import com.store.repos.*;
import com.store.services.*;
import com.store.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    public final StoreService storeService;

    @Autowired
    public DatabaseInserter(CustomerRepository customerRepo, MovieCopyRepository copyRepo, MovieRepository movieRepo, SaleItemRepository saleItemRepo, PurchaseService purchaseService, StoreRepository storeRepo, StoreStockRepository stockRepo, UserRepository userRepo, UserService userService, StoreService storeService) {
        this.customerRepo = customerRepo;
        this.copyRepo = copyRepo;
        this.movieRepo = movieRepo;
        this.saleItemRepo = saleItemRepo;
        this.purchaseService = purchaseService;
        this.storeRepo = storeRepo;
        this.stockRepo = stockRepo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.storeService = storeService;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //TODO rollback failed initializations
//        if (userRepo.existsById(1L)) return;


//        Movie movie1 = new Movie("House of Leaves", Date.valueOf("2000-08-01"),List.of("Horror"));
//        MovieCopy copy1 = new MovieCopy(movie1, 5, 20);
//        StoreStock stock1 = new StoreStock();
//        stock1.addMovieToStock(copy1);
//
//        Store store1 = new Store("test store", stock1);
//
//        List<SaleItem> items = new ArrayList<>();
//        items.add(new SaleItem(copy1, 5));
//
//
//        Customer customer = new Customer("david");
//        storeService.createStore(store1);

//        if (customer.makePurchase(items, store1))
//            System.out.println("initial sale successfull");
//        else
//            System.out.println("initial sale failed");

        //TODO check that stock has only one movie_copy of a given movie
//        Movie movie1 = new Movie("House of Leaves", Date.valueOf("2000-08-01"),List.of("Horror"));
//        MovieCopy copy1 = new MovieCopy(movie1, 3, 20);
//
//        List<MovieCopy> copies = new ArrayList<>();
//        copies.add(copy1);
//
//        StoreStock stock1 = new StoreStock(copies);
//        Store store1 = new Store("test store", stock1);
//
//        SaleItem item1 = new SaleItem(copy1, 2);
//        List<SaleItem> items = new ArrayList<>();
//        items.add(item1);
//
//        Customer customer1 = new Customer("david");
//
//        Sale sale1 = new Sale(items, customer1, store1);
//        store1.getSales().add(sale1);
//
//        storeService.createStore(store1);
//        userService.createUser(new User("user1", "password1", store1));
//
    }
}
