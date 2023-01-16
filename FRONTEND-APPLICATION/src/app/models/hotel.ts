export class Hotel {

  public id: number | undefined;
  public name: string;
  public description: string;
  public imageUrl: string;
  public available: boolean;
  public approved: boolean;

  constructor() {
    this.id = undefined;
    this.name = '';
    this.description = '';
    this.imageUrl = '';
    this.available = false;
    this.approved = false;
  }

}