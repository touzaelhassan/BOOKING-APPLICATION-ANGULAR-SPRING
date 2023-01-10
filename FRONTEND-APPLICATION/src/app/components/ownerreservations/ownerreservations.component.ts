import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { Reservation } from 'src/app/models/reservation';
import { User } from 'src/app/models/user';
import { ReservationService } from 'src/app/services/reservation.service';

@Component({
  selector: 'app-ownerreservations',
  templateUrl: './ownerreservations.component.html',
  styleUrls: ['./ownerreservations.component.css']
})
export class OwnerreservationsComponent {

  public ownerId: any | undefined;
  public reservations: Reservation[] = [];
  private subscriptions: Subscription[] = [];

  constructor(private reservationService: ReservationService) {}

  ngOnInit(): void {
    this.ownerId = this.getUserFromLocalStorage().id
    this.getReservationsByOwnerId(this.ownerId);
  }

  public getReservationsByOwnerId(ownerId: number): void{
    this.subscriptions.push(
      this.reservationService.getReservationsByOwnerId(ownerId).subscribe(
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
