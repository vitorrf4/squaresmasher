import {Component, OnInit} from '@angular/core';
import {StoreService} from "../services/store.service";
import {SearchMovieComponent} from "../search-movie/search-movie.component";
import {StockComponent} from "../stock/stock.component";
import {Router} from "@angular/router";

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css'],
})

export class AppComponent implements OnInit{
  currentTab = "stock"

	constructor(private storeService : StoreService,
              private router: Router) { }

	ngOnInit() {
    this.storeService.getStoreInformation();
	}
}
