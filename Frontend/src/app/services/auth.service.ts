import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
const AUTH_API = 'http://localhost:8080/users/login';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post(
      AUTH_API,
      {
        username,
        password,
      },
      httpOptions
    );
  }

  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post(
      AUTH_API,
      {
        username,
        email,
        password,
      },
      httpOptions
    );
  }

  logout(): Observable<any> {
    return this.http.post(AUTH_API, { }, httpOptions);
  }
}
// import { Injectable } from '@angular/core';
// import {HttpClient, HttpHeaders} from "@angular/common/http";
// import {User} from "../models/user";
//
// @Injectable({
//   providedIn: 'root'
// })
// export class AuthService {
//   authenticated = false;
//
//   constructor(private http: HttpClient) {
//   }
//
//   authenticate(credentials : {name: string, password: string}, callback: Function) {
//     const headers = new HttpHeaders(credentials ? {
//       authorization : 'Basic ' + btoa(credentials.name + ':' + credentials.password)
//     } : {});
//
//     this.http.get<User>('http://localhost:8080/users/login', ).subscribe(response => {
//       this.authenticated = !!response['name'];
//
//       console.log(response);
//       return callback && callback();
//     });
//
//   }
// }
