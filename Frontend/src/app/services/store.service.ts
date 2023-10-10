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

  public callGetStoreApi(userId: number) {
    return this.httpClient.get<Store>(`${this.storeApi}/store/${userId}`);
  }

  public callRestockMoviesApi(movies : Movie[], userId: number) : Observable<Object> {
    return this.httpClient.post(`${this.storeApi}/store/${userId}/restock`, movies);
  }

  public updateStore(store: Store) {
    this.store.next(store);
  }

  public getUpdatedStore(userId: number) {
    this.httpClient.get<Store>(`${this.storeApi}/store/${userId}`).subscribe(res => {
      this.store.next(res);
    });
    return this.store;
  }
}
