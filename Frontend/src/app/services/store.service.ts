import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Store} from "../models/store";
import {Movie} from "../models/movie";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class StoreService {
  public store = new BehaviorSubject<Store>(new Store());

  constructor(private httpClient : HttpClient) {}

  public callGetStoreApi(userId: number) {
    return this.httpClient.get<Store>(`${environment.apiUrl}/store/${userId}`);
  }

  public callRestockMoviesApi(movies : Movie[], userId: number) : Observable<Object> {
    return this.httpClient.post(`${environment.apiUrl}/store/${userId}/restock`, movies);
  }

  public updateStore(store: Store) {
    this.store.next(store);
  }

  public getUpdatedStore(userId: number) {
    this.httpClient.get<Store>(`${environment.apiUrl}/store/${userId}`).subscribe(res => {
      this.store.next(res);
    });
    return this.store;
  }
}
