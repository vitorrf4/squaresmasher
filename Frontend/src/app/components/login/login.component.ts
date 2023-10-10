import {Component, OnInit, ViewChild} from '@angular/core';
import { AuthService } from '../../services/auth.service';
import {Router} from "@angular/router";
import {User} from "../../models/user";

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
    this.auth.authenticate(this.loginCredentials);
    return false;
  }

  registerUser() {
    this.auth.signUp(this.loginCredentials.name, this.loginCredentials.password, this.newUser.storeName).subscribe(res => {
      this.login();
    });
  }

  toggleMode() {
    this.loginCredentials.name = "";
    this.loginCredentials.password = "";
    this.newUser.confirm = "";
    this.newUser.storeName = "";

    this.isLoginMode = !this.isLoginMode;
  }
}
