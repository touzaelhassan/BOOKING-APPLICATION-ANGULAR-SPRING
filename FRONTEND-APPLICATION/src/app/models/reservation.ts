import { Room } from "./room";
import { User } from "./user";

export class Reservation {

  public id?: number;
  public checking:any;
  public checkout:any;
  public room: Room;
  public client: User;
  public approved?:boolean;


  constructor() {
    this.approved = false;
    this.room = new Room();
    this.client = new User();
    this.checking = new Date();
    this.checkout = new Date();

  }

}