import {Component, Input, OnInit} from '@angular/core';
import {Store} from "../../models/store";
import {StoreService} from "../../services/store.service";
import {BehaviorSubject} from "rxjs";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user";

@Component({
  selector: 'app-store',
  templateUrl: './store-header.component.html',
  styleUrls: ['./store-header.component.css']
})
export class StoreHeaderComponent implements OnInit {
  @Input() store! : BehaviorSubject<Store>;
  user : User;

  constructor(private service: StoreService,
              private authService: AuthService) {
    this.user = this.authService.userValue;
  }

  ngOnInit() {
    this.store = this.service.getUpdatedStore(this.user.id);
  }

  logout() {
    this.authService.logout();
  }
}
