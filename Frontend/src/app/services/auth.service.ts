import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user";
import {Router} from "@angular/router";
import {BehaviorSubject, map, Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
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
    return this.http.post<User>(`${environment.apiUrl}/authentication`, credentials).pipe(map(user => {
      user.authdata = window.btoa(credentials.name + ':' + credentials.password);
      localStorage.setItem('user', JSON.stringify(user));

      this.userSubject.next(user);
      this.router.navigateByUrl('/store').then();

      return user;
    }));
  }

  signUp(name: string, password: string, storeName: string) {
    const newUser = {name: name, password: password, storeName: storeName};
    return this.http.post(`${environment.apiUrl}/sign-up`, newUser);
  }

  logout() {
    localStorage.removeItem('user');
    this.userSubject.next(new User());
    this.router.navigateByUrl('/').then();

  }
}
