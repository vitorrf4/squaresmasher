import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../models/user";
import {Router} from "@angular/router";
import {BehaviorSubject, map, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  authenticated = false;
  apiUrl = "http://localhost:8080"
  private userSubject: BehaviorSubject<User>;
  public user: Observable<User>;

  constructor(private http: HttpClient, private router: Router) {
    this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();
  }

  public get userValue(): User {
    return this.userSubject.value;
  }

  authenticate(credentials : {name: string, password: string}) {
			const headers = new HttpHeaders(credentials ? {
				authorization : 'Basic ' + btoa(credentials.name + ':' + credentials.password)
			} : {})

    this.http.post<User>(`${this.apiUrl}/auth/login`, credentials).pipe(map(user => {
      user.authdata = window.btoa(credentials.name + ':' + credentials.password);
      localStorage.setItem('user', JSON.stringify(user));

      this.userSubject.next(user);
      this.router.navigateByUrl("/home");

      return user;
    })).subscribe();
  }

  signUp(name: string, password: string, storeName: string) {
    let newUser = {name: name, password: password, storeName: storeName};
    return this.http.post(`${this.apiUrl}/auth/sign-up`, newUser);
  }

  logout() {
    localStorage.removeItem('user');
    this.userSubject.next(new User());
    this.router.navigate(['/login']);

  }
}
