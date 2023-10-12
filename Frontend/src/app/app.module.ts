import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { HomeComponent } from './components/home/home.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { SaleComponent } from './components/sale/sale-component';
import { SearchMovieComponent } from './components/search-movie/search-movie.component';
import { NgOptimizedImage} from "@angular/common";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule} from "@angular/material/card";
import { StoreHeaderComponent } from './components/store-header/store-header.component';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { StockComponent } from './components/stock/stock.component';
import { LoginComponent } from './components/login/login.component';
import { IndexComponent } from './components/index/index.component';
import {BasicAuthInterceptor } from "./helpers/basic-auth.interceptor";
import {MatSnackBarModule} from '@angular/material/snack-bar'


@NgModule({
  declarations: [
    HomeComponent,
    SaleComponent,
    SearchMovieComponent,
    StoreHeaderComponent,
    StockComponent,
    LoginComponent,
    IndexComponent
  ],
	imports: [
		BrowserModule,
		AppRoutingModule,
		HttpClientModule,
		FormsModule,
		NgOptimizedImage,
		BrowserAnimationsModule,
		MatCardModule,
		ScrollingModule,
		MatSnackBarModule,
		ReactiveFormsModule
	],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true },],
  bootstrap: [IndexComponent]
})
export class AppModule { }
