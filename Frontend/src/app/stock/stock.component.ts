import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.css']
})
export class StockComponent {

  constructor(private router: Router) {
  }

}
