package com.store.controllers;

import com.store.dto.SaleDTO;
import com.store.dto.SaleMapper;
import com.store.models.Sale;
import com.store.models.User;
import com.store.services.RandomSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "RandomSale")
@CrossOrigin
public class RandomSaleController {
    private final RandomSaleService saleService;

    @Autowired
    public RandomSaleController(RandomSaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("purchase/generate/{id}")
    public ResponseEntity<SaleDTO> generateSale(@PathVariable Long id) {
        Sale sale = saleService.generateSale(id);
        if (sale == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        SaleDTO saleDTO = SaleMapper.toDTO(sale);

        return ResponseEntity.ok(saleDTO);
    }
}
