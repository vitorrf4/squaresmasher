import {Component, Input} from '@angular/core';
import {User} from '../models/user';
import {UserService} from '../services/user.service';
import {Router} from '@angular/router';
import {ListUsersComponent} from "../list-users/list-users.component";
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'app-addusers',
  templateUrl: "./adduser.component.html",
  styleUrls: ['./adduser.component.css'],
  providers: [ListUsersComponent]
})

export class AddusersComponent {
  @Input() usersListBehavior!: BehaviorSubject<User[]>;
  user: User;

  constructor(private service: UserService, private userlist : ListUsersComponent, private router: Router) {
    this.user = new User();
  }

  addUser() {
    this.service.addUser(this.user).subscribe(data => {
      const nextWithUser = this.usersListBehavior.value;
      nextWithUser.push(new User(data));
      this.usersListBehavior.next(nextWithUser);
    });
  }
}
