import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { NotificationType } from '../enums/notification-type.enum';
import { AuthenticationService } from '../services/authentication.service';
import { NotificationService } from '../services/notification.service';

@Injectable({ providedIn: 'root'})
export class AuthenticationGuard implements CanActivate {
  constructor(private authenticationService : AuthenticationService, private router: Router, private notificationService: NotificationService) {}
  canActivate( route: ActivatedRouteSnapshot,  state: RouterStateSnapshot): boolean  { return this.isUserLoggedIn(); }
  private isUserLoggedIn(): boolean {
      if(this.authenticationService.isUserLoggedIn()){ return true; }
      this.router.navigate(['/login']);
      this.notificationService.notify(NotificationType.ERROR, `You need to login to access this endpoint`.toUpperCase());
      return false;
  }
}
