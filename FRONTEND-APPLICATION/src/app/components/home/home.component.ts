import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Room } from 'src/app/models/room';
import { RoomService } from 'src/app/services/room.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit{

    public rooms: Room[] = [];
    public refreshing: boolean = false;
    private subscriptions: Subscription[] = [];

    constructor(private roomService: RoomService) {}

    ngOnInit(): void { this.getRooms() }

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

}
