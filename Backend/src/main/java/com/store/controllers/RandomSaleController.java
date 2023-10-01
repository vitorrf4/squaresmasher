package com.store.controllers;

import com.store.dto.SaleDTO;
import com.store.dto.SaleMapper;
import com.store.models.MovieCopy;
import com.store.models.Sale;
import com.store.models.User;
import com.store.repos.UserRepository;
import com.store.services.RandomSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController(value = "RandomSale")
@RequestMapping(name = "/purchases")
@CrossOrigin
public class RandomSaleController {
    private final RandomSaleService saleService;
    private final UserRepository userRepository;

    @Autowired
    public RandomSaleController(RandomSaleService saleService, UserRepository userRepository) {
        this.saleService = saleService;
        this.userRepository = userRepository;
    }

    @GetMapping("/generate/{id}")
    public ResponseEntity<SaleDTO> generateSale(@PathVariable Long id) {
        Sale sale = saleService.generateSale(id);
        if (sale == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        SaleDTO saleDTO = SaleMapper.toDTO(sale);

        return ResponseEntity.ok(saleDTO);
    }

    @GetMapping("/from-user/{id}")
    public ResponseEntity<List<SaleDTO>> getAllSalesByUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) return ResponseEntity.notFound().build();

        List<SaleDTO> salesDTO = new ArrayList<>();

        for (Sale sale : user.get().getStore().getSales())
            salesDTO.add(SaleMapper.toDTO(sale));

        return ResponseEntity.ok(salesDTO);
    }

    @GetMapping("/restock/{id}")
    public ResponseEntity<?> restockMovieCopies(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) return ResponseEntity.notFound().build();

        for (MovieCopy copy : user.get().getStore().getStock().getAllCopies()) {
            copy.addCopies(100);
        }
        user.get().getStore().getStock().calculateTotalCopies();

        return ResponseEntity.noContent().build();
    }
}
