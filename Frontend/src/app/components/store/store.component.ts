import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Store} from "../../models/store";
import {StoreService} from "../../services/store.service";
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.css']
})
export class StoreComponent implements OnInit {
  @Input() store! : BehaviorSubject<Store>;

  constructor(private service: StoreService) { }

  ngOnInit() {
    this.store = this.service.getUpdatedStore();
  }

}
