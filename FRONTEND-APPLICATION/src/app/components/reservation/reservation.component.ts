import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NotificationType } from 'src/app/enums/notification-type.enum';
import { Reservation } from 'src/app/models/reservation';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/services/notification.service';
import { ReservationService } from 'src/app/services/reservation.service';
import { RoomService } from 'src/app/services/room.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit   {

    public loggedInUser: any;
    public room:any;
    private subscriptions: Subscription[] = [];

    constructor(
      private authenticationService: AuthenticationService, 
      private roomService: RoomService, 
      private reservationService: ReservationService, 
       private activatedRoute: ActivatedRoute,
      private router: Router,
      private notificationService: NotificationService
    ){}

    ngOnInit(): void { 
      this.loggedInUser = this.authenticationService.getUserFromLocalStorage();
      this.activatedRoute.paramMap.subscribe(()=>{this.getRoomById()})
    }

    public getRoomById() {
      const id: number = +this.activatedRoute.snapshot.params['id'];
      this.roomService.getRoomById(id).subscribe(
        (data) => { 
          this.room = data;
        }
      )
    }

    public onAddNewReservation(reservationForm: Reservation){
        const formData = this.reservationService.createReservationFormData(this.loggedInUser.id, this.room.id, reservationForm);
      this.subscriptions.push(
        this.reservationService.addReservation(formData).subscribe(
          (response: any) =>{
            this.router.navigateByUrl(`/dashboard`);
            this.notificationService.notify(NotificationType.SUCCESS, `Thanks, your reservation was passed !!.`);
          },  
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
          }
        )
      )
    }

       private sendErrorNotification(notificationType: NotificationType, message: string) : void {
      if(message){
        this.notificationService.notify(notificationType, message);
      }else{
        this.notificationService.notify(notificationType, 'Opps !! error occured, Please try again.')
      }
    }  

}
