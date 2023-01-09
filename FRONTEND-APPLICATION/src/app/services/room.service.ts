import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpEvent, HttpResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Room } from '../models/room';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class RoomService {

    private host = environment.APIEnpointsURL;

    constructor(private http: HttpClient) { }

    public getRooms(): Observable<Room[]>{ return this.http.get<Room[]>(`${this.host}/api/rooms`); }

    public getRoomById(id: number): Observable<Room>{ 
        
        return this.http.get<Room>(`${this.host}/api/room/${id}`); 
    
    }

}
