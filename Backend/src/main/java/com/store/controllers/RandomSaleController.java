package com.store.controllers;

import com.store.models.Sale;
import com.store.models.User;
import com.store.services.RandomSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "RandomSale")
public class RandomSaleController {
    private final RandomSaleService saleService;

    @Autowired
    public RandomSaleController(RandomSaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("purchase/generate")
    public ResponseEntity<Sale> generateSale(@RequestBody User user) {
        Sale sale = saleService.generateSale(user);
        if (sale == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.ok(sale);
    }
}
