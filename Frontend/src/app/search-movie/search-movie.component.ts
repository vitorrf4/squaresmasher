import {Component, Input} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {SearchService} from "../services/search.service";

@Component({
  selector: 'app-search-movie',
  templateUrl: './search-movie.component.html',
  styleUrls: ['./search-movie.component.css']
})
export class SearchMovieComponent {
  @Input() movies! : BehaviorSubject<String[]>;
  query : String = "";

  constructor(private service: SearchService) {
  }

  public searchMovie() {
    this.service.searchMovie(this.query);
    this.movies = this.service.movies;
  }
}
