import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NotificationType } from 'src/app/enums/notification-type.enum';
import { Hotel } from 'src/app/models/hotel';
import { Reservation } from 'src/app/models/reservation';
import { Room } from 'src/app/models/room';
import { User } from 'src/app/models/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { HotelService } from 'src/app/services/hotel.service';
import { NotificationService } from 'src/app/services/notification.service';
import { ReservationService } from 'src/app/services/reservation.service';
import { RoomService } from 'src/app/services/room.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

    public loggedInUser: any;
    public users: User[] = [];
    public hotels: Hotel[] = [];
    public rooms: Room[] = [];
    public reservations: Reservation[] = [];
    private subscriptions: Subscription[] = [];

    constructor(
      private authenticationService: AuthenticationService, 
      private userService: UserService,
      private hotelService: HotelService,
      private roomService: RoomService, 
      private reservationService: ReservationService, 
      private router: Router,
      private notificationService: NotificationService
      ) {}

    ngOnInit(): void { 
      this.loggedInUser = this.authenticationService.getUserFromLocalStorage();
      this.getUsers();
      this.getHotels();
      this.getRooms();
      this.getReservations();
    }

    public getUsers(): void{
      this.subscriptions.push(
        this.userService.getUsers().subscribe(
          (response: User[]) => {
            this.users = response;
          },
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
          }
        )
      )
    }

    public getHotels(): void{
      this.subscriptions.push(
        this.hotelService.getHotels().subscribe(
          (response: Hotel[]) => {
            this.hotels = response;
          },
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
          }
        )
      )
    }

    public getRooms(): void{
      this.subscriptions.push(
        this.roomService.getRooms().subscribe(
          (response: Room[]) => {
            this.rooms = response;
          },
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
          }
        )
      )
    }

    public getReservations(): void{
      this.subscriptions.push(
        this.reservationService.getReservations().subscribe(
          (response: Reservation[]) => {
            this.reservations = response;
          },
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
          }
        )
      )
    }

    public onLogout(){
      this.authenticationService.logout();
      this.router.navigateByUrl('/login');
      this.notificationService.notify(NotificationType.SUCCESS, "You've been successfully logged out !!.")
    }

    private sendErrorNotification(notificationType: NotificationType, message: string) : void {
      if(message){
        this.notificationService.notify(notificationType, message);
      }else{
        this.notificationService.notify(notificationType, 'Opps !! error occured, Please try again.')
      }
    }  

  public ngOnDestroy(): void { this.subscriptions.forEach(sub => sub.unsubscribe()); }

}
