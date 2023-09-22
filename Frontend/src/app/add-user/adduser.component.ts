import {Component, Input, Output} from '@angular/core';
import { User } from '../models/user';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import {UsersComponent} from "../list-users/users.component";

@Component({
  selector: 'app-addusers',
  templateUrl: "./adduser.component.html",
  styleUrls: ['./adduser.component.css'],
  providers: [UsersComponent]
})
export class AddusersComponent {
  user: User;

  constructor(private service: UserService) {
    this.user = new User();
  }

  addUser() {
    this.service.addUser(this.user).subscribe();
  }
}
