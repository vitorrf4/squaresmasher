import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddusersComponent } from './add-user/add-user.component';
import { ListUsersComponent } from './list-users/list-users.component';
import {SearchMovieComponent} from "./search-movie/search-movie.component";
import {SaleComponent} from "./sale/sale-component";

const routes: Routes = [
  { path: "list-users", component: ListUsersComponent },
  { path: "add-user", component: AddusersComponent },
  { path: "sales", component: SaleComponent },
  { path: "search", component: SearchMovieComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
