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

    public showLoading: boolean = false;
    private subscriptions: Subscription[] = [];

    constructor(private router : Router, private authenticationService : AuthenticationService, private notifier: NotificationService) {}

    ngOnInit(): void { 
        if(this.authenticationService.isUserLoggedIn()){
            this.router.navigateByUrl(''); 
        }else{
            this.router.navigateByUrl('/login');
        }
    }

    public onLogin(user: User): void {

        this.showLoading = true;

        this.subscriptions.push(
            this.authenticationService.login(user).subscribe(
              (response: HttpResponse<User>) => {
                  const token : string = response.headers.get(HeaderType.JWT_TOKEN) as string;
                  this.authenticationService.saveTokenInLocalStorage(token);
                  this.authenticationService.saveUserInLocalStorage(response.body as User);
                  this.showLoading = false; 
                  this.router.navigateByUrl('');
              },
              (httpErrorResponse: HttpErrorResponse) => {
                console.log("Error : " + httpErrorResponse);
                this.sendErrorNotification(NotificationType.ERROR, httpErrorResponse.error.message);
                this.showLoading = false;
              }
            )
        );

    }

  private sendErrorNotification(notificationType: NotificationType, message: string) : void {
    if(message){
      this.notifier.notify(notificationType, message);
    }else{
      this.notifier.notify(notificationType, 'Opps !! error occured, Please try again.')
    }
  }

  public ngOnDestroy(): void { this.subscriptions.forEach(sub => sub.unsubscribe()); }

}
