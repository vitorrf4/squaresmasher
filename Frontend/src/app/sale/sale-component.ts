import {Component, Input, OnInit} from '@angular/core';
import {SaleService} from "../services/sale.service";
import {Sale} from "../models/sale";
import {BehaviorSubject} from "rxjs";
import {formatDate} from "@angular/common";
import {StoreService} from "../services/store.service";

@Component({
  selector: 'app-generate-sale',
  templateUrl: './sale-component.html',
  styleUrls: ['./sale-component.css']
})
export class SaleComponent implements OnInit   {
  @Input() sales!: BehaviorSubject<Sale[]>;

  constructor(private saleService: SaleService,
              private storeService: StoreService) { }

  ngOnInit() {
    this.sales = this.saleService.getAllSales();
  }

  public generateSale() {
    this.saleService.getRandomSale();

    setTimeout(() => {
      this.storeService.getStoreInformation();
    }, 500);
  }

  public formatData(date : Date) {
    return formatDate(date, 'HH:mm | dd/MM/yyyy', 'en-US');
  }

  public floatToTwoPrecisionPoints(number : Number) : string {
    return number.toFixed(2);
  }

}
