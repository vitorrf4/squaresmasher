import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {Sale} from "../models/sale";
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SaleService {
  private readonly saleUrl: string = 'http://localhost:8080/sales';
  public sales = new BehaviorSubject<Sale[]>([]);

  constructor(private httpClient : HttpClient) { }

  public generateSale() {
    const headers = new HttpHeaders({'Content-Lenght':"0"});
    const requestOptions = {headers: headers};

    return this.httpClient.post<Sale>(`${this.saleUrl}/1/generate`, requestOptions);
  }

  public getAllSales() : BehaviorSubject<Sale[]> {
    this.httpClient.get<Sale[]>(`${this.saleUrl}/from-user/1`).subscribe(data => {
      this.sales.next(data);
    })
    return this.sales;
  }

}
