import {Component, Input} from '@angular/core';
import {SearchService} from "../../services/search.service";
import {Movie} from "../../models/movie";
import {StoreService} from "../../services/store.service";
import {BehaviorSubject} from "rxjs";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user";
import {MatSnackBar} from "@angular/material/snack-bar";

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
              private authService: AuthService,
              private snackbar: MatSnackBar) {
    this.user = this.authService.userValue;
  }

  public searchMovie() {
    if (!this.query) {
      this.showErrorSnackbar("Search field cannot be empty");
      return;
    }

    this.searchService.searchMovie(this.query).subscribe({
      next: res => {
        this.movies = res;
        if (this.movies.length == 0) this.showErrorSnackbar("No movies found");
      }
    })
  }

  private showErrorSnackbar(message: string) {
    this.snackbar.open(message, "Error" , {
      duration: 4000,
      panelClass: ["error"],
      horizontalPosition: "right",
      verticalPosition: "top"
    });
  }

  public getSelectedMovies() {
    let moviesToAdd : Movie[] = [];

    for (let movie of this.movies) {
      if (movie.copiesAmount >= 10000000 || movie.copiesAmount < 0) {
        this.showErrorSnackbar("Unavailable quantity")
        return;
      }

      if (movie.copiesAmount > 0)
        moviesToAdd.push(movie);
    }

    if (moviesToAdd.length == 0) {
      this.showErrorSnackbar("No copies added");
      return;
    }

    this.restockMovies(moviesToAdd);
  }

  private restockMovies(moviesToAdd: Movie[]) {
    this.storeService.callRestockMoviesApi(moviesToAdd, this.user.id).subscribe(() => {
      this.storeService.getUpdatedStore(this.user.id);
    });

    this.currentTab.next('stock');
  }
}
