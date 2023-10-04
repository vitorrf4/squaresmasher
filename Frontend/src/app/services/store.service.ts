import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Store} from "../models/store";

@Injectable({
  providedIn: 'root'
})
export class StoreService {

  private readonly storeUrl: string = 'http://localhost:8080';
  public store = new BehaviorSubject<Store>(new Store());

  constructor(private httpClient : HttpClient) { }

  public getStoreInformation() {
    this.httpClient.get<Store>(`${this.storeUrl}/store/1`).subscribe(data => {
      console.log(data);
    })
  }
}
