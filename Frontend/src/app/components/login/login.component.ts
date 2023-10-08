import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  credentials = {name: '', password: ''};

  constructor(private auth: AuthService, private http: HttpClient, private router: Router) {
  }

  login() {
    this.auth.authenticate(this.credentials, () => {
      this.router.navigateByUrl("/home");
    });
    return false;
  }

  logout() {
    this.http.post('logout', {}).subscribe(() => {
      this.auth.authenticated = false;
      this.router.navigateByUrl('/login');
    });
  }

}
