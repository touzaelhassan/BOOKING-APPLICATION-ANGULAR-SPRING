import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthenticationService } from './services/authentication.service';
import { UserService } from './services/user.service';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { AuthenticationGuard } from './guards/authentication.guard';
import { NotificationModule } from './notification.module';
import { NotificationService } from './services/notification.service';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { FormsModule } from '@angular/forms';
import { RoomComponent } from './components/room/room.component';
import { ReservationComponent } from './components/reservation/reservation.component';
import { ClientreservationsComponent } from './components/clientreservations/clientreservations.component';
import { OwnerreservationsComponent } from './components/ownerreservations/ownerreservations.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    HomeComponent,
    RoomComponent,
    ReservationComponent,
    ClientreservationsComponent,
    OwnerreservationsComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NotificationModule
  ],
  providers: [
    NotificationService,
    AuthenticationGuard,
    AuthenticationService, 
    UserService, 
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
