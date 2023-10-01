import { Component } from '@angular/core';
import {SaleService} from "../services/sale.service";

@Component({
  selector: 'app-restock',
  styleUrls: ['./restock.component.css'],
  template: `<button (click)="restockCopies()">RESTOCK</button>`
})
export class RestockComponent {

  constructor(private saleService : SaleService) {
  }

  public restockCopies() {
    this.saleService.restockCopies();
  }
}
