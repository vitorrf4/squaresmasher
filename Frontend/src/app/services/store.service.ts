import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
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

  public getStoreInformation() : BehaviorSubject<Store> {
    this.httpClient.get<Store>(`${this.storeUrl}/store/1`).subscribe(data => {
      this.store.next(data);
      console.log(this.store.getValue());
    })
    return this.store;
  }

  public restockMovies(movies : Movie[]) {
    this.httpClient.post(`${this.storeUrl}/store/1/restock`, movies).subscribe();
  }
}
