import { Hotel } from "./hotel";

export class Room {

  public id: number | undefined;
  public name: string;
  public description: string;
  public imageUrl: string;
  public price: number | undefined;
  public available: boolean;
  public hotel: Hotel ;

  constructor() {
    this.id = undefined;
    this.name = '';
    this.description = '';
    this.imageUrl = '';
    this.price = undefined;
    this.available= true;
    this.hotel = new Hotel();
  }

}