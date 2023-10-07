import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Store} from "../models/store";
import {Movie} from "../models/movie";

@Injectable({
  providedIn: 'root'
})
export class StoreService {

  private readonly storeUrl: string = 'http://localhost:8080';
  public store = new BehaviorSubject<Store>(new Store());

  constructor(private httpClient : HttpClient) { }

  public getStoreInformation() {
    return this.httpClient.get<Store>(`${this.storeUrl}/store/1`);
  }

  public restockMovies(movies : Movie[]) : Observable<Object> {
    return this.httpClient.post(`${this.storeUrl}/store/1/restock`, movies);
  }
}
