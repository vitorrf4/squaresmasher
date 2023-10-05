import {Component, OnInit} from '@angular/core';
import {SaleService} from "../services/sale.service";
import {StoreService} from "../services/store.service";

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css'],
})

export class AppComponent implements OnInit{

	constructor(private service: SaleService, private test : StoreService) { }

	ngOnInit() {
    this.test.getStoreInformation();
	}

}
