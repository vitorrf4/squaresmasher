import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './home/app.component';
import { HttpClientModule } from '@angular/common/http';
import { AddusersComponent } from './add-user/add-user.component';
import { ListUsersComponent } from './list-users/list-users.component';
import { FormsModule } from '@angular/forms';
import { UserService } from './services/user.service';
import { GenerateSaleComponent } from './generate-sale/generate-sale.component';
import { RestockComponent } from './restock/restock.component';

@NgModule({
  declarations: [
    AppComponent,
    ListUsersComponent,
    AddusersComponent,
    GenerateSaleComponent,
    RestockComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
