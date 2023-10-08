import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Store} from "../models/store";
import {Movie} from "../models/movie";

@Injectable({
  providedIn: 'root'
})
export class StoreService {

  private readonly storeApi: string = 'http://localhost:8080';
  public store = new BehaviorSubject<Store>(new Store());

  constructor(private httpClient : HttpClient) { }

  public callGetStoreApi() {
    return this.httpClient.get<Store>(`${this.storeApi}/store/1`);
  }

  public callRestockMoviesApi(movies : Movie[]) : Observable<Object> {
    return this.httpClient.post(`${this.storeApi}/store/1/restock`, movies);
  }

  public updateStore(store: Store) {
    this.store.next(store);
  }

  public getUpdatedStore() {
    this.httpClient.get<Store>(`${this.storeApi}/store/1`).subscribe(res => {
      this.store.next(res);
    });
    return this.store;
  }
}
