import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse} from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Room } from '../models/room';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class RoomService {

    private host = environment.APIEnpointsURL;

    constructor(private http: HttpClient) { }

    public getRooms(): Observable<Room[]>{ return this.http.get<Room[]>(`${this.host}/api/rooms`); }
    public getRoomsByOwnerId(id: number): Observable<Room[]>{ return this.http.get<Room[]>(`${this.host}/api/rooms/owner/${id}`); }
    public getRoomById(id: number): Observable<Room>{ return this.http.get<Room>(`${this.host}/api/room/${id}`); }
    public addRoom(formData: FormData): Observable<Room | HttpErrorResponse> {  return this.http.post<Room>(`${this.host}/api/room/add`, formData); }
    public deleteRoom(id: number): Observable<any | HttpErrorResponse> { return this.http.delete<any>(`${this.host}/api/room/delete/${id}`); } 

    public createRoomFormData( ownerUsername: any, room: any, roomImage: File): FormData {
        const formData = new FormData();
        formData.append('hotelId', room.hotelId);
        formData.append('name', room.name);
        formData.append('description', room.description);
        formData.append('ownerUsername', ownerUsername);
        formData.append('roomImage', roomImage);
        formData.append('isAvailable', JSON.stringify(room.isAvailable));
        return formData;
    }

}
