import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  credentials = {username: '', password: ''};

  constructor(private app: AuthService, private http: HttpClient, private router: Router) {
    this.app.authenticate(new User(), router.navigateByUrl("login").then);
  }

  login() {

  }

  logout() {
    this.http.post('logout', {}).subscribe(() => {
      this.app.authenticated = false;
      this.router.navigateByUrl('/login');
    });
  }

}
