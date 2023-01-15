export class Hotel {

  public id: number | undefined;
  public name: string;
  public description: string;
  public imageUrl: string;
  public isAvailable: boolean;
  public isApproved: boolean;

  constructor() {
    this.id = undefined;
    this.name = '';
    this.description = '';
    this.imageUrl = '';
    this.isAvailable = false;
    this.isApproved = false;
  }

}