import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddusersComponent } from './add-user/adduser.component';
import { UsersComponent } from './list-users/users.component';

const routes: Routes = [
  { path: "users", component: UsersComponent},
  { path: "add-user", component: AddusersComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
