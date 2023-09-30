import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {Sale} from "../models/sale";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SaleService {
  private readonly userUrl: string = 'http://localhost:8080/purchase/generate';
  public sales  = new BehaviorSubject<Sale[]>([]);

  constructor(private httpClient : HttpClient) { }

  public getRandomSale() {
    this.httpClient.get(`${this.userUrl}/13`).subscribe(data => {
      console.log(data);
      this.sales.getValue().push(data);
    })
  }

}
