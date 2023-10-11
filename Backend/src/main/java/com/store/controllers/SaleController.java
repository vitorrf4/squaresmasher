package com.store.controllers;

import com.store.dto.SaleDTO;
import com.store.dto.SaleMapper;
import com.store.models.*;
import com.store.repos.UserRepository;
import com.store.services.RandomSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController(value = "RandomSale")
@RequestMapping(path = "/sales")
@CrossOrigin
public class SaleController {
    private final RandomSaleService saleService;
    private final UserRepository userRepository;
    private final HttpHeaders jsonHeaders = new HttpHeaders();

    @Autowired
    public SaleController(RandomSaleService saleService, UserRepository userRepository) {
        this.saleService = saleService;
        this.userRepository = userRepository;
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Transactional
    @PostMapping("/{id}/generate")
    public ResponseEntity<?> generateSale(@PathVariable Long id) {
        Store store = saleService.getUserStore(id);
        if (store == null) return new ResponseEntity<>(new StringBuilder("Invalid user store"), jsonHeaders, HttpStatus.BAD_REQUEST);

        List<Movie> moviesInStock = store.getStock().getAllCopies();
        int moviesQuantityInStock = saleService.getMoviesInStockAmount(moviesInStock);
        if (moviesQuantityInStock == 0) return new ResponseEntity<>(new StringBuilder("No movies in stock"), HttpStatus.NOT_FOUND);

        SaleItem randomSaleItem = saleService.getRandomSaleItem(moviesInStock);
        Sale sale = saleService.generateSale(randomSaleItem, store);
        if (sale == null) return new ResponseEntity<>(new StringBuilder("Sale could not be completed"), HttpStatus.INTERNAL_SERVER_ERROR);

        SaleDTO saleDTO = SaleMapper.toDTO(sale);
        return ResponseEntity.ok(saleDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllSalesByUser(@PathVariable Long id) {

        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) return new ResponseEntity<>(new StringBuilder(HttpStatus.NOT_FOUND.toString()), jsonHeaders, HttpStatus.NOT_FOUND);

        List<SaleDTO> salesDTO = new ArrayList<>();

        for (Sale sale : user.get().getStore().getSales())
            salesDTO.add(SaleMapper.toDTO(sale));

        return ResponseEntity.ok(salesDTO);
    }

}
