import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SaleService} from "../../services/sale.service";
import {Sale} from "../../models/sale";
import {BehaviorSubject} from "rxjs";
import {StoreService} from "../../services/store.service";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user";

@Component({
  selector: 'app-generate-sale',
  templateUrl: './sale-component.html',
  styleUrls: ['./sale-component.css']
})
export class SaleComponent implements OnInit {
  @ViewChild("btnStatus") statusButton!: ElementRef<HTMLButtonElement>
  sales = new BehaviorSubject<Sale[]>([]);
  salesIntervalId: number = 0;
  storeStatus = "closed";
  user : User;

  constructor(private saleService: SaleService,
              private storeService: StoreService,
              private authService: AuthService) {
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

  public generateRandomSale() {
    this.salesIntervalId = setInterval(this.generate.bind(this), 1500);
  }

  public generate() {
    if (this.storeService.store.getValue().copiesTotal == 0) {
      this.changeStoreStatus();
      return;
    }

    this.makeSale();
  }


  public makeSale() {
    this.saleService.generateSale(this.user.id).subscribe(res => {
      this.sales.value.push(res);

      this.storeService.callGetStoreApi(this.user.id).subscribe(res => {
        this.storeService.updateStore(res);
      });
    });
  }
}
