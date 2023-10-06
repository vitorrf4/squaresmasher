import {Component, OnInit} from '@angular/core';
import {SaleService} from "../services/sale.service";
import {Sale} from "../models/sale";
// import {BehaviorSubject} from "rxjs";
import {formatDate} from "@angular/common";
import {StoreService} from "../services/store.service";

@Component({
  selector: 'app-generate-sale',
  templateUrl: './sale-component.html',
  styleUrls: ['./sale-component.css']
})
export class SaleComponent implements OnInit   {
  sales!: Sale[];

  constructor(private saleService: SaleService,
              private storeService: StoreService) { }

  ngOnInit() {
    this.saleService.getAllSales().subscribe(res => {
      console.log(res)
      this.sales = res;
    });
  }

  public generateSale() {
    this.saleService.generateSale().subscribe(res => {
      // this.sales.value.push(res);
      this.storeService.getStoreInformation().subscribe();
    });
  }

  public formatData(date : Date) {
    return formatDate(date, 'HH:mm dd/MM/yyyy', 'en-US');
  }
}
