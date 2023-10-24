package com.store.controllers;

import com.store.dto.MovieDTO;
import com.store.dto.MovieMapper;
import com.store.dto.StoreDTO;
import com.store.dto.StoreMapper;
import com.store.models.Movie;
import com.store.models.Store;
import com.store.repos.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/store")
public class StoreController {
    private final StoreRepository storeRepository;

    @Autowired
    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStoreInformation(@PathVariable Long id) {
        Optional<Store> store = storeRepository.findById(id);
        if (store.isEmpty()) return ResponseEntity.notFound().build();

        StoreDTO storeDTO = StoreMapper.toDTO(store.get());

        return ResponseEntity.ok(storeDTO);
    }

    @PostMapping("/{id}/restock")
    public ResponseEntity<Integer> restockMovies(@PathVariable Long id, @RequestBody List<MovieDTO> movieDTOS) {
        Optional<Store> store = storeRepository.findById(id);
        if (store.isEmpty()) return ResponseEntity.notFound().build();

        int copiesInStockBefore = store.get().getStock().getCopiesTotal();

        for (MovieDTO movieDTO : movieDTOS) {
            if (movieDTO.copiesAmount() > 10000000) return ResponseEntity.badRequest().build();
            Movie movie = MovieMapper.toMovie(movieDTO);
            store.get().getStock().addMovieToStock(movie);
        }

        storeRepository.save(store.get());
        int copiesInStockAfter = store.get().getStock().getCopiesTotal() - copiesInStockBefore;

        return ResponseEntity.ok(copiesInStockAfter);

    }
}
