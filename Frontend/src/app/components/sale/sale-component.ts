import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SaleService} from "../../services/sale.service";
import {Sale} from "../../models/sale";
import {BehaviorSubject} from "rxjs";
import {StoreService} from "../../services/store.service";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-generate-sale',
  templateUrl: './sale-component.html',
  styleUrls: ['./sale-component.css']
})
export class SaleComponent implements OnInit {
  @ViewChild("btnStatus") statusButton!: ElementRef<HTMLButtonElement>
  sales = new BehaviorSubject<Sale[]>([]);
  salesIntervalId = 0;
  storeStatus = "closed";
  user : User;

  constructor(private saleService: SaleService,
              public storeService: StoreService,
              private authService: AuthService,
              private snackBar: MatSnackBar) {
    this.user = this.authService.userValue
  }

  ngOnInit() {
    this.saleService.getAllSales(this.user.id).subscribe(res => {
      this.sales.next(res);
    });
  }

  public changeStoreStatus() {
    switch (this.storeStatus) {
      case "closed":
        this.storeStatus = "open";
        this.generateRandomSale();
        this.statusButton.nativeElement.className = "btn-lg btn-success";
        break;
      case "open":
        this.storeStatus = "closed";
        clearInterval(this.salesIntervalId);
        this.statusButton.nativeElement.className = "btn-lg btn-danger active";
        break;
    }
  }

  public showEmptyStockSnackBar() {
    this.snackBar.open("Stock is empty", "", {
      duration: 4000,
      panelClass: ["error"],
      verticalPosition: "bottom"
    })
  }

  public generateRandomSale() {
    if (this.storeService.store.getValue().copiesTotal == 0) {
      this.showEmptyStockSnackBar();
      setTimeout(() => this.changeStoreStatus(), 100);
      return;
    }

    this.makeSaleWithInterval();
  }

  private makeSaleWithInterval() {
    this.salesIntervalId = setInterval(() => {
      if (this.isPreviousSaleCompleted) {
        this.makeSale();
      }
    }, 1500);
  }

  private isPreviousSaleCompleted: boolean = true;

  public makeSale() {
    this.isPreviousSaleCompleted = false;

    this.saleService.generateSale(this.user.id).subscribe({
      next: saleRes => {
        this.storeService.callGetStoreApi(this.user.id).subscribe(storeRes => {
          this.sales.value.push(saleRes);
          this.storeService.updateStore(storeRes);
          if (storeRes.copiesTotal == 0) this.changeStoreStatus();
          this.isPreviousSaleCompleted = true;
        })
      },
      error: () => {
        this.changeStoreStatus();
        this.showEmptyStockSnackBar();
        this.isPreviousSaleCompleted = true;
      }
    });
  }

}
