import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NotificationType } from 'src/app/enums/notification-type.enum';
import { Hotel } from 'src/app/models/hotel';
import { Room } from 'src/app/models/room';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { HotelService } from 'src/app/services/hotel.service';
import { NotificationService } from 'src/app/services/notification.service';
import { RoomService } from 'src/app/services/room.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit{

    public hotels: Hotel[] = [];
    public rooms: Room[] = [];
    private subscriptions: Subscription[] = [];
    public loggedInUser: any;
    public isUserLoggedIn = false;
    public selectedRoom = new Room();

    constructor(
      private authenticationService: AuthenticationService, 
      private roomService: RoomService, 
      private hotelService: HotelService,
      private router: Router,
      private notificationService: NotificationService
      ) {}

    ngOnInit(): void { 
      this.loggedInUser = this.authenticationService.getUserFromLocalStorage();
      if(this.loggedInUser != null){ this.isUserLoggedIn = true}
      this.getRooms(); 
      this.getHotels(); 
    }

    public getHotels(): void{
      this.subscriptions.push(
        this.hotelService.getHotels().subscribe(
          (response: Hotel[]) => {
            this.hotels = response;
          },
          (httpErrorResponse: HttpErrorResponse) => {
            console.log(httpErrorResponse.error.message)
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
            console.log(httpErrorResponse.error.message)
          }
        )
      )
    }

    public onBooking(roomId:any){
      document.getElementById("hide-room-details-btn")?.click();
      this.router.navigateByUrl(`/reservation/${roomId}`);
    }

    public onSelectRoom(selectedRoom: Room){
      this.selectedRoom  = selectedRoom;
      document.getElementById("openRoomInfo")?.click();
    }

    public onLogout(){
      this.authenticationService.logout();
      this.isUserLoggedIn = false;
      this.router.navigateByUrl('/login');
      this.notificationService.notify(NotificationType.SUCCESS, "You've been successfully logged out !!.")
    }

}
