import {Component, ElementRef, ViewChild} from '@angular/core';
import { AuthService } from '../../services/auth.service';
import {catchError} from "rxjs";
import {HttpErrorResponse, HttpStatusCode} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  @ViewChild("errorDiv") errorDiv! : ElementRef<HTMLParagraphElement>;
  @ViewChild('errorP') errorP! : ElementRef<HTMLParagraphElement>;
  isLoginMode = true;
  loginCredentials = {name: '', password: ''};
  newUser = {storeName: '', confirm: ''};

  constructor(private auth: AuthService) {}

  onSubmit() {
		if (!this.validateLogin()) return;

    if (!this.isLoginMode){
			if (!this.validateRegister()) return;

			this.registerUser();
    }

  }

  login() {
    this.auth.authenticate(this.loginCredentials).subscribe({
      next: res => {
        console.log(res);
      },
      error: () => {
        this.errorP.nativeElement.textContent = 'Invalid username or password';
        this.errorDiv.nativeElement.hidden = false;
      }
    });
  }

  validateLogin(): boolean {
    if (!this.loginCredentials.name || !this.loginCredentials.password) {
      this.errorP.nativeElement.textContent = 'All the fields must be filled';
      this.errorDiv.nativeElement.hidden = false;
      return false;
    }

    return true;
  }

  validateRegister() : boolean {
		if (!this.newUser.storeName || !this.newUser.confirm) {
			this.errorP.nativeElement.textContent = 'All the fields must be filled';
			this.errorDiv.nativeElement.hidden = false;
			return false;
		}

    if (this.newUser.confirm != this.loginCredentials.password) {
			this.errorP.nativeElement.textContent = "Passwords don't match";
			this.errorDiv.nativeElement.hidden = false;
			return false;
    }

    return true;
  }

  registerUser() {
    this.auth.signUp(this.loginCredentials.name, this.loginCredentials.password, this.newUser.storeName).subscribe(() => {
      this.login();
    });
  }

  toggleMode() {
    this.loginCredentials.name = '';
    this.loginCredentials.password = '';
    this.newUser.confirm = '';
    this.newUser.storeName = '';

    this.isLoginMode = !this.isLoginMode;
  }
}
