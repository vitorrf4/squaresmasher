package com.store.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class StoreStock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @OneToMany private List<MovieCopy> copies;
    private int totalCopies;


    public StoreStock() { }

    public StoreStock(Long id, List<MovieCopy> copies, int totalCopies) {
        this.id = id;
        this.copies = copies;
        this.totalCopies = totalCopies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MovieCopy> getCopies() {
        return copies;
    }

    public void setCopies(List<MovieCopy> copies) {
        this.copies = copies;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreStock that = (StoreStock) o;
        return totalCopies == that.totalCopies && Objects.equals(id, that.id) && Objects.equals(copies, that.copies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, copies, totalCopies);
    }
}
