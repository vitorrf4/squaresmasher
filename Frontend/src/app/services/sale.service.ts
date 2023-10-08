import { Injectable } from '@angular/core';
import {Sale} from "../models/sale";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SaleService {
  private readonly saleUrl: string = 'http://localhost:8080/sales';

  constructor(private httpClient : HttpClient) { }

  public generateSale() : Observable<Sale> {
    const headers = new HttpHeaders({'Content-Lenght':"0"});
    const requestOptions = {headers: headers};

    return this.httpClient.post<Sale>(`${this.saleUrl}/1/generate`, requestOptions);
  }

  public getAllSales() : Observable<Sale[]> {
    return this.httpClient.get<Sale[]>(`${this.saleUrl}/from-user/1`);
  }
}
