import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpEvent, HttpResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Room } from '../models/room';
import { Observable } from 'rxjs';
import { Reservation } from '../models/reservation';

@Injectable({ providedIn: 'root' })
export class ReservationService {

    private host = environment.APIEnpointsURL;

    constructor(private http: HttpClient) { }

    public addReservation(client_id:number, room_id:number): Observable<any>{
      return this.http.get<any>(`${this.host}/api/reservation/add/${client_id}/${room_id}`); 
    }

    public getReservations(): Observable<Reservation[]>{ return this.http.get<Reservation[]>(`${this.host}/api/reservations`); }    
    public getReservationsByClientId(clientId: number): Observable<any>{
      return this.http.get<any>(`${this.host}/api/client/reservations/${clientId}`); 
    }

    public getReservationsByOwnerId(ownerId: number): Observable<any>{
      return this.http.get<any>(`${this.host}/api/owner/reservations/${ownerId}`); 
    }

}
