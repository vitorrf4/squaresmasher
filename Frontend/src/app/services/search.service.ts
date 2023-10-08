import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Movie } from "../models/movie";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  constructor(private httpClient : HttpClient) { }

  public searchMovie(query: String) : Observable<Movie[]> {
    return this.httpClient.get<Movie[]>(`http://localhost:8080/movies/search/${query}`);
  }

}
