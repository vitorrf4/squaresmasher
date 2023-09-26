package com.store.services;

import com.store.models.Customer;
import com.store.models.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    public StoreService storeService;
    public CustomerService customerService;
    public MovieService movieService;
    public SaleService saleService;

    @Autowired
    public PurchaseService(StoreService storeService, CustomerService customerService, MovieService movieService, SaleService saleService) {
        this.storeService = storeService;
        this.customerService = customerService;
        this.movieService = movieService;
        this.saleService = saleService;
    }

    public void makePurchase(Customer customer, Sale sale) {

    }
}
