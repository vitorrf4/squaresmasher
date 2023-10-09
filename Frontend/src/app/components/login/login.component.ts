import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  credentials = {name: '', password: ''};

  constructor(private auth: AuthService, private router: Router) {
  }

  login() {
    this.auth.authenticate(this.credentials, () => {
      this.router.navigateByUrl('/home');
    });
    return false;
  }
}
