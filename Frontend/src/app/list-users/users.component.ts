import {Component, Directive, ElementRef, EventEmitter, Injectable, OnInit, Output, ViewChild} from '@angular/core';
import { User } from '../models/user';
import { UserService } from '../services/user.service';

@Component({
	selector: 'app-users',
	templateUrl: './users.component.html',
	styleUrls: ['./users.component.css'],
})
@Injectable()
export class UsersComponent implements OnInit {
	users: User[] = [];

	constructor(private service: UserService) { }

	ngOnInit() {
		this.service.findAll().subscribe(data => {
			this.users = data;
		});
	}

	deleteUser(id : Number) {
		this.service.deleteUser(id).subscribe();
	}
}
