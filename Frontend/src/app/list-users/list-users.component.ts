import {Component, Injectable, Input, OnInit} from '@angular/core';
import { User } from '../models/user';
import { UserService } from '../services/user.service';
import {BehaviorSubject, Observable} from "rxjs";
import {Router} from "@angular/router";

@Component({
	selector: 'app-users',
	templateUrl: './list-users.component.html',
	styleUrls: ['./list-users.component.css'],
})

@Injectable()
export class ListUsersComponent implements OnInit {
	@Input() usersListBehavior!: BehaviorSubject<User[]>;

	constructor(private service: UserService, private router: Router) { }

	ngOnInit() {
		this.service.findAll().subscribe(data => {
			this.usersListBehavior.next(data);
		});
	}

	deleteUser(id : number) {
		this.service.deleteUser(id).subscribe();
		const newUserList = this.usersListBehavior.value.filter(u => u.id != id);
		this.usersListBehavior.next(newUserList);
	}
}
