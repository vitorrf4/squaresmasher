import {Component, Input, OnInit} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {Store} from "../../models/store";
import {StoreService} from "../../services/store.service";

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.css']
})
export class StockComponent implements OnInit{
  @Input() store = new BehaviorSubject<Store>(new Store());

  constructor(private storeService: StoreService) { }

  ngOnInit() {
    this.store = this.storeService.getUpdatedStore();
  }

  public orderMoviesByCopies() {
    console.log(this.store.getValue());
    return this.store.getValue().moviesInStock.sort((m1, m2) =>
      m1.copiesAmount < m2.copiesAmount ? 1 :
        m1.copiesAmount == m2.copiesAmount ? 0 : -1
    )
  }

}
