import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor( private authenticationService: AuthenticationService) {}

    public intercept(httpRequest: HttpRequest<any>, httpHandler: HttpHandler): Observable<HttpEvent<any>> {

        if (httpRequest.url.includes(`${this.authenticationService.host}/api/authentication/register`)){ return httpHandler.handle(httpRequest); }
        if (httpRequest.url.includes(`${this.authenticationService.host}/api/authentication/login`)){ return httpHandler.handle(httpRequest); }
        if (httpRequest.url.includes(`${this.authenticationService.host}/api/hotels`)){ return httpHandler.handle(httpRequest); }
        if (httpRequest.url.includes(`${this.authenticationService.host}/api/rooms`)){ return httpHandler.handle(httpRequest); }
        if (httpRequest.url.includes(`${this.authenticationService.host}/api/room`)){ return httpHandler.handle(httpRequest); }

        this.authenticationService.loadToken();
        const token = this.authenticationService.getToken();
        const request = httpRequest.clone({ setHeaders:{ Authorization: `Bearer ${token}` } })
        return httpHandler.handle(request);

    }
}
