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

    if (!this.isLoginMode){
			if (!this.validateRegister()) return;

			this.registerUser();
    }

		this.auth.authenticate(this.loginCredentials);

    if (this.auth.userValue == null || this.auth.userValue.id == -1) {
			this.errorP.nativeElement.textContent = 'Invalid username or password';
			this.errorDiv.nativeElement.hidden = false;
    }
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
    this.auth.signUp(this.loginCredentials.name, this.loginCredentials.password, this.newUser.storeName).subscribe();
  }

  toggleMode() {
    this.loginCredentials.name = '';
    this.loginCredentials.password = '';
    this.newUser.confirm = '';
    this.newUser.storeName = '';

    this.isLoginMode = !this.isLoginMode;
  }
}
