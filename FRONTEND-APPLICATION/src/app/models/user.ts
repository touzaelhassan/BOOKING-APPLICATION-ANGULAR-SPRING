export class User {
    public id: number | undefined;
    public userId: string;
    public firstname: string;
    public lastname: string;
    public username: string;
    public email: string;
    public lastLoginDate: Date | null;
    public lastLoginDateDisplay: Date | null;
    public joinDate: Date | null;
    public profileImageUrl: string;
    public active: boolean;
    public notLocked: boolean;
    public role: string;
    public authorities: [];

  constructor() {
    this.id = undefined;
    this.userId = '';
    this.firstname = '';
    this.lastname = '';
    this.username = '';
    this.email = '';
    this.lastLoginDate = null ;
    this.lastLoginDateDisplay = null;
    this.joinDate = null;
    this.profileImageUrl = '';
    this.active = false;
    this.notLocked = false;
    this.role = '';
    this.authorities = [];
  }
}