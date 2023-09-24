import {Component, Input, OnInit} from '@angular/core';
import { User } from '../models/user';
import { UserService } from '../services/user.service';
import {BehaviorSubject} from "rxjs";
import {Router} from "@angular/router";

@Component({
	selector: 'app-users',
	templateUrl: './list-users.component.html',
	styleUrls: ['./list-users.component.css'],
})

export class ListUsersComponent implements OnInit {
	@Input() users!: BehaviorSubject<User[]>;

	constructor(private service: UserService, private router: Router) { }

	ngOnInit() {
        this.users = this.service.getList();
	}

	deleteUser(id : number) {
		this.service.deleteUser(id);
	}
}
