import { Injectable } from '@angular/core';
import {Sale} from "../models/sale";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class SaleService {
  constructor(private httpClient : HttpClient) { }

  public generateSale(userId: number) : Observable<Sale> {
    const headers = new HttpHeaders({'Content-Lenght':"0"});

    return this.httpClient.post<Sale>(`${environment.apiUrl}/sales/${userId}/generate`, {headers: headers});
  }

  public getAllSales(userId: number) : Observable<Sale[]> {
    return this.httpClient.get<Sale[]>(`${environment.apiUrl}/sales/${userId}`);
  }
}
