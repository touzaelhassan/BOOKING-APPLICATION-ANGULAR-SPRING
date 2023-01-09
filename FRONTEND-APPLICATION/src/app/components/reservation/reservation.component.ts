import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { ReservationService } from 'src/app/services/reservation.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

  public client_id:any | undefined;
  public room_id:any | undefined;
  private subscriptions: Subscription[] = [];

  constructor(private route : ActivatedRoute, private reservationService: ReservationService) {}
  
  ngOnInit(): void { 
    this.client_id = this.route.snapshot.params['client_id'];
    this.room_id = this.route.snapshot.params['room_id'];
    this.addReservation(this.client_id, this.room_id)
  }

  public addReservation(client_id: number, room_id: number): void{
      this.subscriptions.push(
      this.reservationService.addReservation(client_id, room_id).subscribe(
        (response: any) => { console.log(response) },
        (httpErrorResponse: HttpErrorResponse) => { console.log(httpErrorResponse.error.message); }
      )
    )
  }

}
