import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from "../models/user";
import {BehaviorSubject} from 'rxjs';

@Injectable()
export class UserService {
  private readonly userUrl: string = 'http://localhost:8080/users';
  public users = new BehaviorSubject<User[]>([]);

  constructor(private http: HttpClient) { }

    public getList(): BehaviorSubject<User[]> {
        this.http.get<User[]>(this.userUrl).subscribe({
            next: res => this.users.next(res),
            error : err => alert(err.message)
        });

        return this.users;
    }

    public addUser(user : User) {
        this.http.post(this.userUrl, user).subscribe({
            next: res=> this.users.getValue().push(new User(res)),
            error: err => alert(err.message)
        });
	}

    public deleteUser(id : Number){
        this.http.delete(`${this.userUrl}/${id}`).subscribe({
            next: () => {
                const listWithoutDeletedUser = this.users.value.filter(u => u.id != id);
                this.users.next(listWithoutDeletedUser);
            },
            error: err => alert(err.message)
        });
    }

}
