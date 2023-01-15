import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NotificationType } from 'src/app/enums/notification-type.enum';
import { CustomHttpRespone } from 'src/app/models/custom-http-response';
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

    public users: User[] = [];
    public hotels: Hotel[] = [];
    public rooms: Room[] = [];
    public reservations: Reservation[] = [];
    private subscriptions: Subscription[] = [];

    public loggedInUser: any;
    public selectedUser?: any;
    public editedUser = new User();
    private currentUsername?: string;
    public profileImage: any;
    public hotelImage: any;

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
      this.getUsers(false);
      this.getHotels();
      this.getRooms();
      this.getReservations();
    }

    public getUsers(displayNotification:boolean): void{
      this.subscriptions.push(
        this.userService.getUsers().subscribe(
          (response: User[]) => {
            this.users = response;
            this.userService.addUsersToLocalStorage(this.users);
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


    public onProfileImageChange(event:any): void{
      const files = event.target.files;
      this.profileImage = files[0];
    }

    public saveNewUser(): void{ document.getElementById("new-user-save")?.click(); }

    public onAddNewUser(userForm: any): void{
      const formData = this.userService.createUserFormData(null, userForm, this.profileImage);
      this.subscriptions.push(
        this.userService.addUser(formData).subscribe(
          (response: any) =>{
            document.getElementById("new-user-close")?.click();
            this.getUsers(false);
            this.profileImage = null;
            userForm.reset()
            this.notificationService.notify(NotificationType.SUCCESS, `The new user was added successfully !!.`);
          },  
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
            this.profileImage = null;
          }
        )
      )
    }

    public onHotelImageChange(event:any): void{
      const files = event.target.files;
      this.hotelImage = files[0];
    }


    public saveNewHotel(): void{ document.getElementById("new-hotel-save")?.click(); }
    public onAddNewHotel(hotelForm: any): void{
      const formData = this.hotelService.createHotelFormData(this.loggedInUser.username ,hotelForm, this.hotelImage);
      this.subscriptions.push(
        this.hotelService.addHotel(formData).subscribe(
          (response: any) =>{
            document.getElementById("new-hotel-close")?.click();
            this.getUsers(false);
            this.hotelImage = null;
            this.notificationService.notify(NotificationType.SUCCESS, `The new hotel was added successfully !!.`);
          },  
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
            this.profileImage = null;
          }
        )
      )
    }

    public onEditUser(user: User): void{
      this.editedUser = user;
      this.currentUsername = user.username;
      document.getElementById("openUserEdit")?.click();
    }

    public onUpdateUser(): void{
      const formData = this.userService.createUserFormData(this.currentUsername, this.editedUser , this.profileImage);
      this.subscriptions.push(
        this.userService.updateUser(formData).subscribe(
          (response: any) =>{
            document.getElementById("closeEditUserModalButton")?.click();
            this.sendErrorNotification(NotificationType.SUCCESS, `The user information were updated successfully !!.`);
            this.getUsers(false);
            this.profileImage = null;
          },  
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
            this.profileImage = null;
          }
        )
      )
    }

    public onDelete(id: any){
      this.subscriptions.push(
        this.userService.deleteUser(id).subscribe(
          (response: CustomHttpRespone)=>{
            this.sendErrorNotification(NotificationType.SUCCESS, response.message);
            this.getUsers(false);
          },
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
          }
        )
      )
    }

    public onSelectUser(selectedUser: User){
      this.selectedUser  = selectedUser;
      console.log(selectedUser.firstname);
      console.log(selectedUser.profileImageUrl);
      document.getElementById("openUserInfo")?.click();
    }

  public searchInUsersList(keyword: string){
      const searchResults: User[] = [];
      for (const user of this.userService.getUsersFromLocalStorage()){
        if(
          user.firstname.toLocaleLowerCase().indexOf(keyword.toLocaleLowerCase()) !== -1 || 
          user.lastname.toLocaleLowerCase().indexOf(keyword.toLocaleLowerCase()) !== -1 ||
          user.username.toLocaleLowerCase().indexOf(keyword.toLocaleLowerCase()) !== -1 
        ){
          searchResults.push(user);
        }
      }
      this.users = searchResults;
      if (searchResults.length == 0 || !keyword) { this.users = this.userService.getUsersFromLocalStorage(); }
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
