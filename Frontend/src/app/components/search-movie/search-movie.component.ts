import {Component, ElementRef, Input, ViewChild} from '@angular/core';
import {SearchService} from "../../services/search.service";
import {Movie} from "../../models/movie";
import {StoreService} from "../../services/store.service";
import {BehaviorSubject} from "rxjs";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user";

@Component({
  selector: 'app-search-movie',
  templateUrl: './search-movie.component.html',
  styleUrls: ['./search-movie.component.css']
})
export class SearchMovieComponent {
  @Input() currentTab!: BehaviorSubject<string>
  movies: Movie[] = [];
  query = '';
  user: User;

  constructor(private searchService: SearchService,
              private storeService: StoreService,
              private authService: AuthService) {
    this.user = this.authService.userValue;
  }

  public searchMovie() {
    this.searchService.searchMovie(this.query).subscribe({
      next: res => this.movies = res
    })
  }

  public restockMovies() {
    let moviesToAdd : Movie[] = [];

    for (let movie of this.movies) {
      if (movie.copiesAmount > 0)
        moviesToAdd.push(movie)
    }
    //TODO validate maximum int input

    this.storeService.callRestockMoviesApi(moviesToAdd, this.user.id).subscribe(() => {
      this.storeService.getUpdatedStore(this.user.id);
    });

    this.currentTab.next('stock');
  }
}
