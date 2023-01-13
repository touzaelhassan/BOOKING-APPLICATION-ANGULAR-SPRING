import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Room } from 'src/app/models/room';
import { User } from 'src/app/models/user';
import { RoomService } from 'src/app/services/room.service';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})

export class RoomComponent implements OnInit {

  public client_id?:number;
  public room_id!: number;
  public room: Room = new Room();
  private subscriptions: Subscription[] = [];

  constructor(private route : ActivatedRoute, private roomService: RoomService) {}
  
  ngOnInit(): void { 
    this.client_id = this.getUserFromLocalStorage()?.id
    this.room_id = this.route.snapshot.params['id'];
    this.getRoomById(this.room_id);
  }

  public getRoomById(id: number): void{
    this.subscriptions.push(
      this.roomService.getRoomById(id).subscribe(
        (response: Room) => { this.room = response; },
        (httpErrorResponse: HttpErrorResponse) => { console.log(httpErrorResponse.error.message); }
      )
    )
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
