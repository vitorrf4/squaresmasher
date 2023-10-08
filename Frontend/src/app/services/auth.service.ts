import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../models/user";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  authenticated = false;

  constructor(private http: HttpClient) {
  }

  authenticate(credentials : {name: string, password: string}, callback: Function) {
    const headers = new HttpHeaders(credentials ? {
      authorization : 'Basic ' + btoa(credentials.name + ':' + credentials.password)
    } : {});

    this.http.get<User>('http://localhost:8080/users/login', {headers: headers}).subscribe(response => {
      this.authenticated = !!response['name'];

      console.log(response);
      return callback && callback();
    });

  }
}
