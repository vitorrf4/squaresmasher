import {Component, ElementRef, ViewChild} from '@angular/core';
import { AuthService } from '../../services/auth.service';

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

    if (this.isLoginMode){
      this.login();
      return;
    }

    if (!this.validateRegister()) return;
    this.registerUser();
  }

  login() {
    this.auth.authenticate(this.loginCredentials).subscribe({
      error: () => {
        this.showErrorDiv('Invalid username or password');
      }
    });
  }

  validateLogin(): boolean {
    if (!this.loginCredentials.name || !this.loginCredentials.password) {
      this.showErrorDiv("All the fields must be filled");
      return false;
    }

    return true;
  }

  validateRegister() : boolean {
		if (!this.newUser.storeName || !this.newUser.confirm) {
      this.showErrorDiv("All the fields must be filled");
			return false;
		}

    if (this.newUser.confirm != this.loginCredentials.password) {
      this.showErrorDiv("Passwords don't match");
			return false;
    }

    return true;
  }

  registerUser() {
    this.auth.signUp(this.loginCredentials.name, this.loginCredentials.password, this.newUser.storeName).subscribe({
      next: () => this.login(),
      error: err => {
        if (err.status == "409")
          this.showErrorDiv('Username already taken');
      }
    });
  }

  showErrorDiv(message: string) {
    this.errorP.nativeElement.textContent = message;
    this.errorDiv.nativeElement.hidden = false;
  }

  toggleMode() {
    this.loginCredentials.name = '';
    this.loginCredentials.password = '';
    this.newUser.confirm = '';
    this.newUser.storeName = '';

    this.isLoginMode = !this.isLoginMode;
  }
}
