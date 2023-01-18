import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NotificationType } from 'src/app/enums/notification-type.enum';
import { Role } from 'src/app/enums/role.enum';
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

   @Input() admin: boolean = false; 
   @Input() owner: boolean = false; 
   @Input() client: boolean = false; 

    public users: User[] = [];
    public hotels: Hotel[] = [];
    public rooms: Room[] = [];
    public reservations: Reservation[] = [];
    private subscriptions: Subscription[] = [];
    public loggedInUser: any;
    public selectedUser?: any;
    private currentUsername?: string;
    public editedUser = new User();
    public editedHotel = new Hotel();
    public editedRoom = new Room();
    public editedReservation = new Reservation();
    public profileImage: any;
    public hotelImage: any;
    public roomImage: any;

    constructor(
      private authenticationService: AuthenticationService, 
      private userService: UserService,
      private hotelService: HotelService,
      private roomService: RoomService, 
      private reservationService: ReservationService, 
      private router: Router,
      private notificationService: NotificationService
    ){}

    ngOnInit(): void { 
      this.loggedInUser = this.authenticationService.getUserFromLocalStorage();
      this.getUsers();
      this.getHotels();
      this.getRooms();
      this.getReservations();
    }

    public getUserRole(): string { return this.authenticationService.getUserFromLocalStorage().role; }
    public get isAdmin(): boolean{ return this.getUserRole() === Role.SUPER_ADMIN || this.getUserRole() === Role.ADMIN; }
    public get isOwner(): boolean{ return this.getUserRole() === Role.OWNER; }
    public get isClient(): boolean{ return this.getUserRole() === Role.CLIENT }

    public getUsers(): void{
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

    public getHotelsByOwnerId(id: number): void{
      this.subscriptions.push(
        this.hotelService.getHotelsByOwnerId(id).subscribe(
          (response: Hotel[]) => {
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

    public getRoomsByOwnerId(id: number): void{
      this.subscriptions.push(
        this.roomService.getRoomsByOwnerId(id).subscribe(
          (response: Room[]) => {
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
            this.getUsers();
            this.profileImage = null;
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
      console.log(hotelForm);
      const formData = this.hotelService.createHotelFormData(this.loggedInUser.username ,hotelForm, this.hotelImage);
      this.subscriptions.push(
        this.hotelService.addHotel(formData).subscribe(
          (response: any) =>{
            document.getElementById("new-hotel-close")?.click();
            this.getHotels();
            this.hotelImage = null;
            this.notificationService.notify(NotificationType.SUCCESS, `The new hotel was added successfully !!.`);
          },  
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
            this.hotelImage = null;
          }
        )
      )
    }

    public onRoomImageChange(event:any): void{
      const files = event.target.files;
      this.roomImage = files[0];
    }

    public saveNewRoom(): void{ document.getElementById("new-room-save")?.click(); }

    public onAddNewRoom(roomForm: any): void{
      const formData = this.roomService.createRoomFormData(this.loggedInUser.username, roomForm, this.roomImage);
        this.subscriptions.push(
          this.roomService.addRoom(formData).subscribe(
            (response: any) =>{
              document.getElementById("new-room-close")?.click();
              this.getRooms();
              this.roomImage = null;
              this.notificationService.notify(NotificationType.SUCCESS, `The new room was added successfully !!.`);
            },  
            (httpErrorResponse: HttpErrorResponse) => {
              this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
              this.roomImage = null;
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
            this.getUsers();
            this.profileImage = null;
          },  
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
            this.profileImage = null;
          }
        )
      )
    }

    public onEditHotel(hotel: Hotel): void{
      this.editedHotel = hotel;
      document.getElementById("openHotelEdit")?.click();
    }

    public onUpdateHotel(): void{
      const hotelOwner = this.editedHotel.owner.username;
      const formData = this.hotelService.createHotelFormData(hotelOwner, this.editedHotel , this.hotelImage);
      this.subscriptions.push(
        this.hotelService.updateHotel(formData).subscribe(
          (response: any) =>{
            document.getElementById("closeEditHotelModalButton")?.click();
            this.sendErrorNotification(NotificationType.SUCCESS, `The user information were updated successfully !!.`);
            this.getHotels();
            this.hotelImage = null;
          },  
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
            this.hotelImage = null;
          }
        )
      )
    }

    public changeReservationStatus(reservation: Reservation){
        this.subscriptions.push(
        this.reservationService.changeReservationStatus(reservation).subscribe(
          (response: any) =>{
            this.sendErrorNotification(NotificationType.SUCCESS, `The Reservation status was updated successfully !!.`);
            this.getReservations();
          },  
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
          }
        )
      )
    }

    public onEditRoom(room: Room): void{
      this.editedRoom = room;
      document.getElementById("openRoomEdit")?.click();
    }

    public onUpdateRoom(): void{
      const formData = this.hotelService.createHotelFormData(this.loggedInUser.username, this.editedRoom , this.hotelImage);
      this.subscriptions.push(
        this.hotelService.updateHotel(formData).subscribe(
          (response: any) =>{
            document.getElementById("closeEditHotelModalButton")?.click();
            this.sendErrorNotification(NotificationType.SUCCESS, `The user information were updated successfully !!.`);
            this.getHotels();
            this.hotelImage = null;
          },  
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
            this.hotelImage = null;
          }
        )
      )
    }


    public onDeleteUser(id: any){
      this.subscriptions.push(
        this.userService.deleteUser(id).subscribe(
          (response: CustomHttpRespone)=>{
            this.sendErrorNotification(NotificationType.SUCCESS, response.message);
            this.getUsers();
          },
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
          }
        )
      )
    }

    public onDeleteHotel(id: any){
      this.subscriptions.push(
        this.hotelService.deleteHotel(id).subscribe(
          (response: CustomHttpRespone)=>{
            this.getHotels();
            this.getHotelsByOwnerId(this.loggedInUser.id);
            this.sendErrorNotification(NotificationType.SUCCESS, response.message);
          },
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
          }
        )
      )
    }

    public onDeleteRoom(id: any){
      this.subscriptions.push(
        this.roomService.deleteRoom(id).subscribe(
          (response: CustomHttpRespone)=>{
            this.getRooms();
            this.sendErrorNotification(NotificationType.SUCCESS, response.message);
          },
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
          }
        )
      )
    }

    public deleteReservation(id: any){
      this.subscriptions.push(
        this.reservationService.deleteReservation(id).subscribe(
          (response: CustomHttpRespone)=>{
            this.getReservations();
            this.sendErrorNotification(NotificationType.SUCCESS, response.message);
          },
          (httpErrorResponse: HttpErrorResponse) => {
            this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
          }
        )
      )
    }

    public onSelectUser(selectedUser: User){
      this.selectedUser  = selectedUser;
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
