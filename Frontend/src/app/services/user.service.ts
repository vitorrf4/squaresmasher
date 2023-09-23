import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from "../models/user";
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable()
export class UserService {
  private readonly userUrl: string;
  public usersListBehaviorSubject = new BehaviorSubject<User[]>([]);

  constructor(private http: HttpClient) {
    this.userUrl = "http://localhost:8080/users";
  }

  public getList(): BehaviorSubject<User[]> {
    this.http.get<User[]>(`${this.userUrl}`).subscribe(
      data => this.usersListBehaviorSubject.next(data));
    return this.usersListBehaviorSubject;
  }

	public addUser(user : User) {
    this.http.post(`${this.userUrl}`, user).subscribe(data => {
      this.usersListBehaviorSubject.getValue().push(new User(data));
    });
	}

	public deleteUser(id : Number){
    this.http.delete(`${this.userUrl}/${id}`).subscribe(data => {
      const listWithoutDeletedUser = this.usersListBehaviorSubject.value.filter(u => u.id != id);
      this.usersListBehaviorSubject.next(listWithoutDeletedUser);
    })
	}
}
