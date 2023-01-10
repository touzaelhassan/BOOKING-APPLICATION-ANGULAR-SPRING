import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor( private authenticationService : AuthenticationService, private router : Router) {}

  ngOnInit(): void {
    if(!this.authenticationService.isUserLoggedIn()){
      this.router.navigateByUrl('/login');
    }else{
      this.authenticationService.logout();
    }
  }

}
