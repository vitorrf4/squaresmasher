package com.store.services;

import com.store.models.Customer;
import com.store.models.Sale;
import com.store.models.SaleItem;
import com.store.models.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseService {
    public StoreService storeService;
    public CustomerService customerService;
    public MovieService movieService;
    public SaleService saleService;
    public SaleItemService saleItemService;

    @Autowired
    public PurchaseService(StoreService storeService, CustomerService customerService, MovieService movieService, SaleService saleService, SaleItemService saleItemService) {
        this.storeService = storeService;
        this.customerService = customerService;
        this.movieService = movieService;
        this.saleService = saleService;
        this.saleItemService = saleItemService;
    }

    @Transactional
    public void makePurchase(List<SaleItem> purchases, Customer customer, Store store) {
        Sale sale = new Sale(purchases, customer);

        store.getSales().add(sale);
        storeService.changeStore(store);
    }
}
