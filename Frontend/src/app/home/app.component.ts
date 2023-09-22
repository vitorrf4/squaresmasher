import {Component, OnInit} from '@angular/core';
import { UserService } from '../services/user.service';
import { User } from '../models/user';
import {BehaviorSubject} from "rxjs";

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css'],
})

export class AppComponent implements OnInit{
  public usersListRoot = new BehaviorSubject<User[]>([]);

	constructor() { }

	ngOnInit() {
		let name: string = Math.random().toString();
		let user = new User();
		user.name = name;

		// setInterval(this.generateRandomUser, 3000, this.service);
	}

	generateRandomUser(service: UserService) {
		var name : string = (Math.random() + 1000).toString();
		var user = new User();
		user.name = name;

		service.addUser(user).subscribe();
		console.log(`user ${user.name} added`)
	}

}
