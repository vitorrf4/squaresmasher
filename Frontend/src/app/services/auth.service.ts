import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../models/user";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  authenticated = false;
  apiUrl = "http://localhost:8080"

  constructor(private http: HttpClient, private router: Router) {

  }

  authenticate(credentials : {name: string, password: string}, callback: Function) {
    const headers = new HttpHeaders(credentials ? {
      authorization : 'Basic ' + btoa(credentials.name + ':' + credentials.password)
    } : {});

    this.http.get<Object>(`${this.apiUrl}/auth/login`, {headers: headers}).subscribe(response => {
      // @ts-ignore
      this.authenticated = !!response['name'];

      console.log(response);
      return callback && callback();
    });
  }

  logout() {
    this.authenticated = false;
    this.router.navigateByUrl('/login');

  }
}
