import {Component, Input} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {SearchService} from "../services/search.service";
import {Movie} from "../models/movie";
import {StoreService} from "../services/store.service";

@Component({
  selector: 'app-search-movie',
  templateUrl: './search-movie.component.html',
  styleUrls: ['./search-movie.component.css']
})
export class SearchMovieComponent {
  @Input() movies! : BehaviorSubject<Movie[]>;
  query : String = "";

  constructor(private searchService: SearchService, private storeService: StoreService) { }

  public searchMovie() {
    this.searchService.searchMovie(this.query);
    this.movies = this.searchService.movies;
  }

  public restockMovies() {
    let moviesToAdd : Movie[] = [];

    for (let movie of this.movies.value) {
      if (movie.copiesAmount > 0)
        moviesToAdd.push(movie)
    }

    this.storeService.restockMovies(moviesToAdd);
  }
}
