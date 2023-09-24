import {Component, Input} from '@angular/core';
import {User} from '../models/user';
import {UserService} from '../services/user.service';
import {Router} from '@angular/router';
import {ListUsersComponent} from "../list-users/list-users.component";
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'app-addusers',
  templateUrl: "./add-user.component.html",
  styleUrls: ['./add-user.component.css'],
  providers: [ListUsersComponent]
})

export class AddusersComponent {
  @Input() users!: BehaviorSubject<User[]>;
  user: User;

  constructor(private service: UserService) {
    this.user = new User();
  }

  addUser() {
    this.service.addUser(this.user);
  }
}
