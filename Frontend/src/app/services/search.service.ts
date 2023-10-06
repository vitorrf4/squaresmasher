import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Movie } from "../models/movie";

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  constructor(private httpClient : HttpClient) { }

  public searchMovie(query: String) {
    return this.httpClient.get<Movie[]>(`http://localhost:8080/movies/search/${query}`);
  }

}
