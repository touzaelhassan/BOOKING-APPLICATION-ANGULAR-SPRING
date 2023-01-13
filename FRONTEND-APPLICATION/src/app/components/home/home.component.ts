import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Hotel } from 'src/app/models/hotel';
import { Room } from 'src/app/models/room';
import { HotelService } from 'src/app/services/hotel.service';
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

    constructor(private roomService: RoomService, private hotelService: HotelService) {}

    ngOnInit(): void { 
      this.getRooms(); 
      this.getHotels(); 
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

}
