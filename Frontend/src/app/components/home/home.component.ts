import {Component, Input} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {AuthService} from "../../services/auth.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
	selector: 'app-home',
	templateUrl: './home.component.html',
	styleUrls: ['./home.component.css'],
})

export class HomeComponent {
  currentTab = new BehaviorSubject<string>("stock")

  constructor(private auth: AuthService, private router: Router) {
    if (!this.authenticated()){
      console.log("no auth")
      this.router.navigateByUrl("/login");
    }
  }

  logout() {
    this.auth.logout();
  }

  authenticated() {return this.auth.authenticated}
}
