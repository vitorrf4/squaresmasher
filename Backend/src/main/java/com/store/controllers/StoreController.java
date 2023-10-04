package com.store.controllers;

import com.store.dto.MovieDTO;
import com.store.dto.MovieMapper;
import com.store.dto.StoreDTO;
import com.store.dto.StoreMapper;
import com.store.models.Movie;
import com.store.models.Store;
import com.store.models.User;
import com.store.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/store")
@CrossOrigin
public class StoreController {
    private final UserRepository userRepository;

    @Autowired
    public StoreController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @GetMapping("/{id}")
    public ResponseEntity<?> getStoreInformation(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) return ResponseEntity.notFound().build();

        Store store = user.get().getStore();

        StoreDTO storeDTO = StoreMapper.toDTO(store);

        return ResponseEntity.ok(storeDTO);
    }


    @PostMapping("/{id}/restock")
    public ResponseEntity<Integer> restockMovies(@PathVariable Long id, @RequestBody List<MovieDTO> movieDTOS) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) return ResponseEntity.notFound().build();

        int copiesInStockBefore = user.get().getStore().getStock().getCopiesTotal();

        for (MovieDTO dto : movieDTOS) {
            logger.info("DTO: " + dto.toString());
            Movie movie = MovieMapper.toMovie(dto);
            logger.info("Movie: " + movie);
            user.get().getStore().getStock().addMovieToStock(movie);
        }

        userRepository.save(user.get());
        int copiesInStockAfter = user.get().getStore().getStock().getCopiesTotal() - copiesInStockBefore;

        return ResponseEntity.ok(copiesInStockAfter);

    }
}
