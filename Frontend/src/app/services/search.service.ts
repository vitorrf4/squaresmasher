import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Movie} from "../models/movie";

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private readonly searchUrl: string = 'http://localhost:8080';
  // public movies = new BehaviorSubject<Movie[]>([]);

  constructor(private httpClient : HttpClient) { }

  public searchMovie(query: String) {
    return this.httpClient.get<Movie[]>(`${this.searchUrl}/movies/search/${query}`);
  }

}
