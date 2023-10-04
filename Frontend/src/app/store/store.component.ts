import {Component, OnInit} from '@angular/core';
import {Store} from "../models/store";
import {StoreService} from "../services/store.service";

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.css']
})
export class StoreComponent implements OnInit {
  private store!: Store;

  constructor(private service: StoreService) { }

  ngOnInit() {
    this.service.getStoreInformation();
  }
}
