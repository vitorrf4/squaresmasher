import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from "../models/user";
import { Observable } from 'rxjs';

@Injectable()
export class UserService {
  private userUrl: string;

  constructor(private http: HttpClient) {
    this.userUrl = "http://localhost:8080/users";
  }

  public findAll() : Observable<User[]>{
    return this.http.get<User[]>(`${this.userUrl}/list`);
  }

  public addUser(user : User) {
    return this.http.post(`${this.userUrl}/add`, user);
  }
  public deleteUser(id : Number){
    return this.http.delete(`${this.userUrl}/delete/${id}`)
  }
}
