import {Component, Input} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {AuthService} from "../../services/auth.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {User} from "../../models/user";

@Component({
	selector: 'app-home',
	templateUrl: './home.component.html',
	styleUrls: ['./home.component.css'],
})

export class HomeComponent {
  currentTab = new BehaviorSubject<string>("stock")
	user!: User;

  constructor(private auth: AuthService, private router: Router) {
		this.auth.user.subscribe(res => {
			this.user = res
		});
  }
}
