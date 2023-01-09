export class Room {

  public id: number | undefined;
  public name: string;
  public isAvailable: boolean;

  constructor() {
    this.name = '';
    this.isAvailable = true;
    this.id = undefined;
  }

}