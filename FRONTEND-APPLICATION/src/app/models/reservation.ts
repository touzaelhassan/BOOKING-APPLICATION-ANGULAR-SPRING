import { Room } from "./room";
import { User } from "./user";

export class Reservation {

  public id?: number;
  public isApproved?:boolean;
  public room: Room;
  public client: User;

  constructor() {
    this.isApproved = false;
    this.room = new Room();
    this.client = new User();
  }

}