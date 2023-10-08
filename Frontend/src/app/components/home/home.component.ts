import {Component, Input} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Component({
	selector: 'app-root',
	templateUrl: './home.component.html',
	styleUrls: ['./home.component.css'],
})

export class HomeComponent {
  currentTab = new BehaviorSubject<string>("stock")
}
