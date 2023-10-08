package com.store.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.Year;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("movieTitle")
    private String title; //TODO change to movieTitle
    private int copiesAmount;
    private double unitPrice;
    private Year releaseYear;
    @JsonAlias("poster_path")
    private String posterUrl;


    public Movie() { }

    public Movie(String title, int copiesAmount, Year releaseYear) {
        this.title = title;
        this.copiesAmount = copiesAmount;
        this.releaseYear = releaseYear;
        calculateUnitPrice();
    }

    public Movie(String title, int copiesAmount, Year releaseYear, String posterUrl) {
        this.title = title;
        this.copiesAmount = copiesAmount;
        this.releaseYear = releaseYear;
        this.posterUrl = posterUrl;
        calculateUnitPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String movie) {
        this.title = movie;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void calculateUnitPrice() {
        String currentYear = Year.now().toString();
        if (currentYear.equals(releaseYear.toString())) {
            unitPrice = 19.99; // if it's a new release(year of release is the current year) price is a fixed $20
            return;
        }

        int currentDecade = Integer.parseInt(currentYear.substring(0, 3));
        int movieReleaseDecade = Integer.parseInt(releaseYear.toString().substring(0, 3));

        int decadePrice = currentDecade - movieReleaseDecade;

        unitPrice = (5 + decadePrice) - 0.01;
    }

    public Year getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Year releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getCopiesAmount() {
        return copiesAmount;
    }

    public void addCopies(int copiesAdded) {
        //TODO test for negative numbers
        copiesAmount += copiesAdded;
    }

    public boolean takeCopies(int copiesTaken) {
        if (copiesTaken > copiesAmount) return false;

        copiesAmount -= copiesTaken;
        return true;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return copiesAmount == movie.copiesAmount && Objects.equals(id, movie.id) && Objects.equals(title, movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, copiesAmount);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", movieTitle='" + title + '\'' +
                ", copiesAmount=" + copiesAmount +
                ", unitPrice=" + unitPrice +
                ", releaseYear=" + releaseYear +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}
