import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {Sale} from "../models/sale";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SaleService {
  private readonly purchaseUrl: string = 'http://localhost:8080/purchases';
  public sales = new BehaviorSubject<Sale[]>([]);

  constructor(private httpClient : HttpClient) { }

  public getRandomSale() {
    this.httpClient.get<Sale>(`${this.purchaseUrl}/generate/1`).subscribe(data => {
      console.log(data);
      this.sales.getValue().push(new Sale(data));
    })
  }

  public getAllSales() {
    this.httpClient.get<Sale[]>(`${this.purchaseUrl}/from-user/1`).subscribe(data => {
      console.log(data);
      this.sales.next(data);
    })
  }

  public restockCopies() {
    this.httpClient.get(`${this.purchaseUrl}/restock/1`).subscribe(
      data => console.log(data)
    )
  }

}
