import {Component, OnInit, ViewChild} from '@angular/core';
import { AuthService } from '../../services/auth.service';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  @ViewChild("form", {static: false}) form : any
  isLoginMode = true; // Initially, show the login form
  loginCredentials = {name: '', password: ''};
  newUser = {storeName: '', confirm: ''};

  constructor(private auth: AuthService, private router: Router) {
  }

  onSubmit() {
    if (this.isLoginMode)
      this.login();
    else
      this.registerUser();

  }

  login() {
    this.auth.authenticate(this.loginCredentials, () => {
      this.router.navigateByUrl('/home');
    });
    return false;
  }

  registerUser() {

  }


  toggleMode() {
    this.loginCredentials.name = "";
    this.loginCredentials.password = "";
    this.newUser.confirm = "";
    this.newUser.storeName = "";
    this.isLoginMode = !this.isLoginMode;
  }

  protected readonly onsubmit = onsubmit;
}
