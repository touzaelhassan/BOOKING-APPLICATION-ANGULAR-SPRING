import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NotificationType } from 'src/app/enums/notification-type.enum';
import { User } from 'src/app/models/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/services/notification.service';
import { ReservationService } from 'src/app/services/reservation.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

  public client_id:any;
  public room_id:any;
  private subscriptions: Subscription[] = [];

  constructor(
    private route : ActivatedRoute, 
    private router : Router, 
    private reservationService: ReservationService,
    private authenticationService : AuthenticationService,
    private notifier: NotificationService
    ) {}
  
  ngOnInit(): void {

    if(!this.authenticationService.isUserLoggedIn()){

      this.sendNotification(NotificationType.WARNING, `You need to login to make a reservation`)
      this.router.navigateByUrl('/login');
      
    }else{

      this.client_id = this.getUserFromLocalStorage()?.id
      this.room_id = this.route.snapshot.params['room_id'];
      this.addReservation(this.client_id, this.room_id)
      this.sendNotification(NotificationType.INFO, `You reservation is pending !!! .`)
      this.router.navigateByUrl('/my-reservations');
    }
  }

  public addReservation(client_id: number, room_id: number): void{
      this.subscriptions.push(
      this.reservationService.addReservation(client_id, room_id).subscribe(
        (response: any) => { console.log(response) },
        (httpErrorResponse: HttpErrorResponse) => { console.log(httpErrorResponse.error.message); }
      )
    )
  }

    private sendNotification(notificationType: NotificationType, message: string) : void {
    if(message){
      this.notifier.notify(notificationType, message);
    }else{
      this.notifier.notify(notificationType, 'Opps !! error occured, Please try again.')
    }
  }


  public getUserFromLocalStorage(): User | null { 
    let userData = localStorage.getItem('user');
    if(!userData){
      return null;
    } else{
      return JSON.parse(userData); 
    }
  }
  
}
