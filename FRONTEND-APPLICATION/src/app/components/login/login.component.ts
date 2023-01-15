import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { HeaderType } from 'src/app/enums/header-type.enum';
import { NotificationType } from 'src/app/enums/notification-type.enum';
import { User } from 'src/app/models/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit, OnDestroy{

    private subscriptions: Subscription[] = [];

    constructor(
      private authenticationService : AuthenticationService, 
      private router : Router, 
      private notificationService: NotificationService
    ) {}

    ngOnInit(): void { }

    public onLogin(user: User): void {
        this.subscriptions.push(
            this.authenticationService.login(user).subscribe(
              (response: HttpResponse<User>) => {
                  const token : string = response.headers.get(HeaderType.JWT_TOKEN) as string;
                  this.authenticationService.saveTokenInLocalStorage(token);
                  this.authenticationService.saveUserInLocalStorage(response.body as User);
                  this.notificationService.notify(NotificationType.SUCCESS, "You've been successfully logged in !!.")
                  this.router.navigateByUrl('/dashboard');
              },
              (httpErrorResponse: HttpErrorResponse) => {
                this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
              }
            )
        );
    }

  private sendErrorNotification(notificationType: NotificationType, message: string) : void {
    if(message){
      this.notificationService.notify(notificationType, message);
    }else{
      this.notificationService.notify(notificationType, 'Opps !! error occured, Please try again.')
    }
  }

  public ngOnDestroy(): void { this.subscriptions.forEach(sub => sub.unsubscribe()); }

}
