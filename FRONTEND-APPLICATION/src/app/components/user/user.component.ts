import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, Subscription } from 'rxjs';
import { NotificationType } from 'src/app/enums/notification-type.enum';
import { User } from 'src/app/models/user';
import { NotificationService } from 'src/app/services/notification.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})

export class UserComponent implements OnInit{

    private titleSubject = new BehaviorSubject<string>('Users'); 
    public titleAction$ = this.titleSubject.asObservable();
    public users: User[] = [];
    public refreshing: boolean = false;
    private subscriptions: Subscription[] = [];
    selectedUser: User | undefined ;

    constructor(private userService: UserService, private notificationService: NotificationService) {}

    ngOnInit(): void { this.getUsers(true); }

    public changeTitle(title: string): void{ this.titleSubject.next(title); }

    public getUsers(showNotification: boolean): void{
      this.refreshing = true;
      this.subscriptions.push(
        this.userService.getUsers().subscribe(
          (response: User[]) => {
            this.userService.addUsersToLocalStorage(response);
            this.users = response;
            this.refreshing = false;
            if(showNotification){
                this.sendNotification(NotificationType.SUCCESS, `${response.length} user(s) loaded successfully`)
            }
          },
          (httpErrorResponse: HttpErrorResponse) => {
              this.sendNotification(NotificationType.ERROR, httpErrorResponse.error.message);
              this.refreshing = false;
          }
        )
      )
    }

    private sendNotification(notificationType: NotificationType, message: string) : void {
    if(message){
      this.notificationService.notify(notificationType, message);
    }else{
      this.notificationService.notify(notificationType, 'Opps !! error occured, Please try again.')
    }
  }

}
