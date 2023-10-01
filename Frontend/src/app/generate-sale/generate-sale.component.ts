import {Component, Input, OnInit} from '@angular/core';
import {SaleService} from "../services/sale.service";
import {Sale} from "../models/sale";
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'app-generate-sale',
  templateUrl: './generate-sale.component.html',
  styleUrls: ['./generate-sale.component.css']
})
export class GenerateSaleComponent implements OnInit   {
  @Input() sales!: BehaviorSubject<Sale[]>;

  constructor(private saleService: SaleService ) { }

  ngOnInit() {
    this.saleService.getAllSales();
    this.sales = this.saleService.sales
  }

  public generateSale() {
    this.saleService.getRandomSale();
    this.sales = this.saleService.sales
  }

}
