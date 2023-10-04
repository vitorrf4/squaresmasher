package com.store.controllers;

import com.store.dto.MovieDTO;
import com.store.dto.MovieMapper;
import com.store.dto.SaleDTO;
import com.store.dto.SaleMapper;
import com.store.models.*;
import com.store.repos.UserRepository;
import com.store.services.RandomSaleService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController(value = "RandomSale")
@RequestMapping(path = "/purchases")
@CrossOrigin
public class RandomSaleController {
    private final RandomSaleService saleService;
    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    public RandomSaleController(RandomSaleService saleService, UserRepository userRepository) {
        this.saleService = saleService;
        this.userRepository = userRepository;
    }

    @Transactional
    @GetMapping("/generate/{id}")
    public ResponseEntity<?> generateSale(@PathVariable Long id) {
        Store store = saleService.getUserStore(id);
        if (store == null) return new ResponseEntity<>(new StringBuilder("Invalid user store"), HttpStatus.BAD_REQUEST);

        List<Movie> moviesInStock = store.getStock().getAllCopies();

        SaleItem randomSale = saleService.getRandomSaleItem(moviesInStock);
        if (randomSale == null) return new ResponseEntity<>(new StringBuilder("No movie copies in stock"), HttpStatus.NOT_FOUND);

        Sale sale = saleService.generateSale(randomSale, store);
        if (sale == null) return new ResponseEntity<>(new StringBuilder("Sale could not be completed"), HttpStatus.BAD_REQUEST);

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

        for (Movie copy : user.get().getStore().getStock().getAllCopies()) {
            copy.addCopies(100);
        }
        user.get().getStore().getStock().calculateTotalCopies();
        userRepository.save(user.get());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/restock/{userId}")
    public ResponseEntity<?> restockMovies(@PathVariable Long userId, @RequestBody List<MovieDTO> movieDTOS) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) return ResponseEntity.notFound().build();


        for (MovieDTO dto : movieDTOS) {
            logger.info("DTO: " + dto.toString());
            Movie movie = MovieMapper.toMovie(dto);
            logger.info("DTO: " + movie);
            user.get().getStore().getStock().addMovieToStock(movie);
        }

        userRepository.save(user.get());
        return ResponseEntity.ok().build();

    }
}
