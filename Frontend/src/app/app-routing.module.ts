import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {LoginComponent} from "./components/login/login.component";
import {UserGuard} from "./helpers/user.guard";

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "home", component: HomeComponent, canActivate: [UserGuard]},
  { path: "**", pathMatch: "full", redirectTo: "login" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
