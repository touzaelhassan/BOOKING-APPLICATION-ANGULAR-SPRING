import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './services/authentication.service';
import { ReservationService } from './services/reservation.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit{

  isUserLoggedIn?: boolean;

  constructor(private authenticationService: AuthenticationService, private reservationService: ReservationService){}

  ngOnInit(): void { 
    this.isUserLoggedIn = this.authenticationService.isUserLoggedIn();
  }

}
