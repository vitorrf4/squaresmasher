import {Component, Input} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css'],
})

export class AppComponent{
  currentTab = new BehaviorSubject<string>("stock")
}
