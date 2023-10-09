import {Component, Input} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {AuthService} from "../../services/auth.service";

@Component({
	selector: 'app-home',
	templateUrl: './home.component.html',
	styleUrls: ['./home.component.css'],
})

export class HomeComponent {
  currentTab = new BehaviorSubject<string>("stock")

	constructor(private auth: AuthService) { }
}
