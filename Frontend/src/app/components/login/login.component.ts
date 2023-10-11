import {Component, ViewChild} from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  @ViewChild("form", {static: false}) form : any
  isLoginMode = true;
  loginCredentials = {name: '', password: ''};
  newUser = {storeName: '', confirm: ''};

  constructor(private auth: AuthService,
              private snackBar : MatSnackBar) {}

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
    this.auth.signUp(this.loginCredentials.name, this.loginCredentials.password, this.newUser.storeName).subscribe(() => {
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
