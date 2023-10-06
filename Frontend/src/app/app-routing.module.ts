import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SearchMovieComponent} from "./search-movie/search-movie.component";
import {SaleComponent} from "./sale/sale-component";

const routes: Routes = [
  { path: "sales", component: SaleComponent },
  { path: "search", component: SearchMovieComponent },
  { path: "**", component: SaleComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
