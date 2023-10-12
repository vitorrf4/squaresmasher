import {Component, Input, OnInit} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {Store} from "../../models/store";
import {StoreService} from "../../services/store.service";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user";

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.css']
})
export class StockComponent implements OnInit{
  @Input() store = new BehaviorSubject<Store>(new Store());
  user: User;

  constructor(private storeService: StoreService,
              private authService: AuthService) {
    this.user = this.authService.userValue;
  }

  ngOnInit() {
    this.store = this.storeService.getUpdatedStore(this.user.id);
  }

  public orderMoviesByCopies() {
    return this.store.getValue().moviesInStock.sort((m1, m2) =>
      m1.copiesAmount < m2.copiesAmount ? 1 :
        m1.copiesAmount == m2.copiesAmount ? 0 : -1
    )
  }

}
