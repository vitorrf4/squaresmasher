import {Component} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user";

@Component({
	selector: 'app-home',
	templateUrl: './home.component.html',
	styleUrls: ['./home.component.css'],
})

export class HomeComponent {
  currentTab = new BehaviorSubject<string>("stock")
	user!: User;

  constructor(private auth: AuthService) {
		this.auth.user.subscribe(res => {
			this.user = res
		});
  }
}
