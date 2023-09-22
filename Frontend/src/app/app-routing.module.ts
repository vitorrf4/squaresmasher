import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddusersComponent } from './add-user/adduser.component';
import { ListUsersComponent } from './list-users/list-users.component';

const routes: Routes = [
  { path: "list-users", component: ListUsersComponent},
  { path: "add-user", component: AddusersComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
