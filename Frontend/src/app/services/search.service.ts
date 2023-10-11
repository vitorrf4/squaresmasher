import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Movie } from "../models/movie";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  constructor(private httpClient : HttpClient) { }

  public searchMovie(query: String) : Observable<Movie[]> {
    return this.httpClient.get<Movie[]>(`${environment.apiUrl}/movies/search/${query}`);
  }

}
