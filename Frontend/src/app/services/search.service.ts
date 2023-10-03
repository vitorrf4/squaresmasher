import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private readonly searchUrl: string = 'http://localhost:8080';
  public movies = new BehaviorSubject<String[]>([]);

  constructor(private httpClient : HttpClient) { }

  public searchMovie(query: String) {
    this.httpClient.get<String[]>(`${this.searchUrl}/movies/search/${query}`).subscribe(data => {
      console.log(data);
      this.movies.next(data);
    })
  }
}
