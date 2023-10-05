import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {Sale} from "../models/sale";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SaleService {
  private readonly saleUrl: string = 'http://localhost:8080/sales';
  public sales = new BehaviorSubject<Sale[]>([]);

  constructor(private httpClient : HttpClient) { }

  public getRandomSale() {
    return this.httpClient.get<Sale>(`${this.saleUrl}/generate/1`);
  }

  public getAllSales() : BehaviorSubject<Sale[]> {
    this.httpClient.get<Sale[]>(`${this.saleUrl}/from-user/1`).subscribe(data => {
      this.sales.next(data);
    })
    return this.sales;
  }

}
