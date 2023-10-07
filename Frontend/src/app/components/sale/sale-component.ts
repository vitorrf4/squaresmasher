import {Component, OnInit} from '@angular/core';
import {SaleService} from "../../services/sale.service";
import {Sale} from "../../models/sale";
import {BehaviorSubject, generate} from "rxjs";
import {StoreService} from "../../services/store.service";

@Component({
  selector: 'app-generate-sale',
  templateUrl: './sale-component.html',
  styleUrls: ['./sale-component.css']
})
export class SaleComponent implements OnInit {
  sales = new BehaviorSubject<Sale[]>([]);
  salesIntervalId: number = 0;
  timeout = 1500;
  storeStatus = "open";

  constructor(private saleService: SaleService,
              private storeService: StoreService) { }

  ngOnInit() {
    this.saleService.getAllSales().subscribe(res => {
      this.sales.next(res);
    });
    this.generateRandomSale();
  }

  public changeStoreStatus() {
    if (this.storeStatus == "closed") {
      this.storeStatus = "open";
      this.generateRandomSale();
    } else {
      this.storeStatus = "closed";
      clearInterval(this.salesIntervalId);
    }

  }

  public generateRandomSale() {
    this.salesIntervalId = setInterval(this.generate.bind(this), this.timeout);
  }

  public generate() {
    if (this.storeService.store.getValue().copiesTotal == 0) {
      this.changeStoreStatus();
      return;
    }

    this.makeSale();
  }


  public makeSale() {
    this.saleService.generateSale().subscribe(res => {
      this.sales.value.push(res);

      this.storeService.callGetStoreApi().subscribe(res => {
        this.storeService.updateStore(res);
      });
    });
  }
}
