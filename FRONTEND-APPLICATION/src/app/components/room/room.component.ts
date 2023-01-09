import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Room } from 'src/app/models/room';
import { RoomService } from 'src/app/services/room.service';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})

export class RoomComponent implements OnInit {

  public id:any | undefined;
  public room: Room | undefined;
  private subscriptions: Subscription[] = [];


  constructor(private route : ActivatedRoute, private roomService: RoomService) {}
  
  ngOnInit(): void { 
    this.id = this.route.snapshot.params['id'];
    this.getRoomById(this.id);
  }

      public getRoomById(id: number): void{
      this.subscriptions.push(
        this.roomService.getRoomById(id).subscribe(
          (response: Room) => {
            this.room = response;
            console.log(this.room)
          },
          (httpErrorResponse: HttpErrorResponse) => {
            console.log(httpErrorResponse.error.message)
          }
        )
      )
    }

}
