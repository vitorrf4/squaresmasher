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

  constructor(private authService: AuthService) {}

  onSubmit() {
		if (!this.validateLoginFields()) return;

    if (this.isLoginMode){
      this.signIn();
      return;
    }

    if (!this.validateRegistrationFields()) return;

    this.signUp();
  }


  validateLoginFields(): boolean {
    if (!this.loginCredentials.name || !this.loginCredentials.password) {
      this.showErrorDiv("All the fields must be filled");
      return false;
    }

    return true;
  }

  validateRegistrationFields() : boolean {
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

  signIn() {
    this.authService.signIn(this.loginCredentials).subscribe({
      error: () => {
        this.showErrorDiv('Invalid username or password');
      }
    });
  }

  signUp() {
    this.authService.signUp(this.loginCredentials.name, this.loginCredentials.password, this.newUser.storeName).subscribe({
      next: () => this.signIn(),
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
