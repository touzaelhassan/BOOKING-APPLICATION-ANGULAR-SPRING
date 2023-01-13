import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientreservationsComponent } from './components/clientreservations/clientreservations.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { OwnerreservationsComponent } from './components/ownerreservations/ownerreservations.component';
import { RegisterComponent } from './components/register/register.component';
import { ReservationComponent } from './components/reservation/reservation.component';
import { RoomComponent } from './components/room/room.component';

const routes: Routes = [
    { path: '', component: HomeComponent},
    { path: 'register', component: RegisterComponent},
    { path: 'login', component: LoginComponent},
    { path: 'logout', component: LogoutComponent},
    { path: 'room/:id', component: RoomComponent},
    { path: 'reservation/:room_id', component: ReservationComponent},
    { path: 'my-reservations', component: ClientreservationsComponent},
    { path: 'owner/reservations', component: OwnerreservationsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
