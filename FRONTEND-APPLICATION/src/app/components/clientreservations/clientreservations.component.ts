import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Reservation } from 'src/app/models/reservation';
import { User } from 'src/app/models/user';
import { ReservationService } from 'src/app/services/reservation.service';

@Component({
  selector: 'app-clientreservations',
  templateUrl: './clientreservations.component.html',
  styleUrls: ['./clientreservations.component.css']
})

export class ClientreservationsComponent implements OnInit{
  
  public clientId: any | undefined;
  public reservations: Reservation[] = [];
  private subscriptions: Subscription[] = [];

  constructor(private reservationService: ReservationService) {}

  ngOnInit(): void {
    this.clientId = this.getUserFromLocalStorage().id
    this.getReservationsByClientId(this.clientId);
  }

  public getReservationsByClientId(clientId: number): void{
    this.subscriptions.push(
      this.reservationService.getReservationsByClientId(clientId).subscribe(
        (response: Reservation[]) => {
          this.reservations = response;
          console.log(this.reservations);
        },
        (httpErrorResponse: HttpErrorResponse) => {
          console.log(httpErrorResponse.error.message);
        }
      )
    )
  }

  public getUserFromLocalStorage(): User { return JSON.parse(localStorage.getItem('user') || ''); }

}
