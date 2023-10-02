import {Component, Input, OnInit} from '@angular/core';
import {SaleService} from "../services/sale.service";
import {Sale} from "../models/sale";
import {BehaviorSubject} from "rxjs";
import {formatDate} from "@angular/common";

@Component({
  selector: 'app-generate-sale',
  templateUrl: './generate-sale.component.html',
  styleUrls: ['./generate-sale.component.css']
})
export class GenerateSaleComponent implements OnInit   {
  @Input() sales!: BehaviorSubject<Sale[]>;

  constructor(private saleService: SaleService ) { }

  ngOnInit() {
    this.sales = this.saleService.getAllSales();
  }

  public generateSale() {
    this.saleService.getRandomSale();
  }

  public formatData(date : Date) {
    return formatDate(date, 'HH:mm | dd/MM/yyyy', 'en-US');
  }

  public floatToTwoPrecisionPoints(number : Number) : string {
    return Number.parseFloat(number.toString()).toFixed(2);
  }

}
