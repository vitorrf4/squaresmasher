import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SearchMovieComponent} from "./components/search-movie/search-movie.component";
import {StockComponent} from "./components/stock/stock.component";
import {AppComponent} from "./components/home/app.component";

const routes: Routes = [
  { path: "home", component: AppComponent,
    children: [
      { path: "search", component: SearchMovieComponent },
      { path: "stock", component: StockComponent }
    ]
  },
  { path: "", pathMatch: "full", redirectTo: "home" },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
