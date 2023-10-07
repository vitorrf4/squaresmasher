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
  @Input() store!: BehaviorSubject<Store>;

  constructor(private storeService: StoreService) { }

  ngOnInit() {
    this.store = this.storeService.getStoreInformation();
  }

}
