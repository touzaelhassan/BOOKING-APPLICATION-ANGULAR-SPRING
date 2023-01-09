import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { RegisterComponent } from './components/register/register.component';
import { ReservationComponent } from './components/reservation/reservation.component';
import { RoomComponent } from './components/room/room.component';

const routes: Routes = [
    { path: '', component: HomeComponent},
    { path: 'register', component: RegisterComponent},
    { path: 'login', component: LoginComponent},
    { path: 'logout', component: LogoutComponent},
    { path: 'room/:id', component: RoomComponent},
    { path: 'reservation/:client_id/:room_id', component: ReservationComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
