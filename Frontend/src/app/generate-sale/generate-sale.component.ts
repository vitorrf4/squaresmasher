import { Component } from '@angular/core';
import {SaleService} from "../services/sale.service";
import {Sale} from "../models/sale";

@Component({
  selector: 'app-generate-sale',
  templateUrl: './generate-sale.component.html',
  styleUrls: ['./generate-sale.component.css']
})
export class GenerateSaleComponent {

  constructor(private saleService: SaleService ) { }

  public generateSale() {
    this.saleService.getRandomSale();
  }

}
