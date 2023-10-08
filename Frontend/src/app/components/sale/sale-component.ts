import {Component, OnInit} from '@angular/core';
import {SaleService} from "../../services/sale.service";
import {Sale} from "../../models/sale";
import {BehaviorSubject} from "rxjs";
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
  storeStatus = "closed";

  constructor(private saleService: SaleService,
              private storeService: StoreService) { }

  ngOnInit() {
    this.saleService.getAllSales().subscribe(res => {
      this.sales.next(res);
    });
  }

  public changeStoreStatus() {
    let statusButton = document.getElementById("btn-status");

    if (this.storeStatus == "closed") {
      this.storeStatus = "open";
      this.generateRandomSale();
      // @ts-ignore
      statusButton.className = "btn-lg btn-success";
    } else {
      this.storeStatus = "closed";
      clearInterval(this.salesIntervalId);
      // @ts-ignore
      statusButton.className = "btn-lg btn-danger active";
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