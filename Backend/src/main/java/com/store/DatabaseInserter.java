package com.store;

import com.store.models.*;
import com.store.repos.*;
import com.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.zip.DataFormatException;

@Component
public class DatabaseInserter implements CommandLineRunner {
    public final CustomerRepository customerRepo;
    public final MovieCopyRepository copyRepo;
    public final MovieRepository movieRepo;
    public final SaleItemRepository saleItemRepo;
    public final SaleRepository saleRepo;
    public final StoreRepository storeRepo;
    public final StoreStockRepository stockRepo;
    public final UserRepository userRepo;
    public final UserService userService;

    @Autowired
    public DatabaseInserter(CustomerRepository customerRepo, MovieCopyRepository copyRepo, MovieRepository movieRepo, SaleItemRepository saleItemRepo, SaleRepository saleRepo, StoreRepository storeRepo, StoreStockRepository stockRepo, UserRepository userRepo, UserService userService) {
        this.customerRepo = customerRepo;
        this.copyRepo = copyRepo;
        this.movieRepo = movieRepo;
        this.saleItemRepo = saleItemRepo;
        this.saleRepo = saleRepo;
        this.storeRepo = storeRepo;
        this.stockRepo = stockRepo;
        this.userRepo = userRepo;
        this.userService = userService;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        try {

            User user1 = new User();
            user1.setName("test user");
            user1.setPassword("test password");

            Store store1 = new Store();
            store1.setName("test store");
            user1.setStore(store1);

            Movie movie1 = new Movie();
            movie1.setMovieTitle("House of Leaves");
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
            User createdUserr = userService.createUser(user1);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
    }
}
