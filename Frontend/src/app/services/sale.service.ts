import { Injectable } from '@angular/core';
import {Sale} from "../models/sale";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SaleService {
  private readonly salesApi: string = 'http://localhost:8080/sales';

  constructor(private httpClient : HttpClient) { }

  public generateSale(userId: number) : Observable<Sale> {
    const headers = new HttpHeaders({'Content-Lenght':"0"});

    return this.httpClient.post<Sale>(`${this.salesApi}/${userId}/generate`, {headers: headers});
  }

  public getAllSales(userId: number) : Observable<Sale[]> {
    return this.httpClient.get<Sale[]>(`${this.salesApi}/${userId}`);
  }
}
