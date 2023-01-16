import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse} from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Hotel } from '../models/hotel';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HotelService {

  private host = environment.APIEnpointsURL;

  constructor(private http: HttpClient) { }

    public addHotel(formData: FormData): Observable<Hotel | HttpErrorResponse> { 
        return this.http.post<Hotel>(`${this.host}/api/hotel/add`, formData); 
    }

    public updateHotel(formData: FormData): Observable<Hotel | HttpErrorResponse> { 
        return this.http.put<Hotel>(`${this.host}/api/hotel/update`, formData); 
    }

  public getHotels(): Observable<Hotel[]>{ return this.http.get<Hotel[]>(`${this.host}/api/hotels`); }

   public createHotelFormData( ownerUsername: any, hotel: any, hotelImage: File): FormData {
        const formData = new FormData();
        formData.append('id', hotel.id);
        formData.append('name', hotel.name);
        formData.append('description', hotel.description);
        formData.append('city', hotel.city);
        formData.append('ownerUsername', ownerUsername);
        formData.append('hotelImage', hotelImage);
        formData.append('isAvailable', JSON.stringify(hotel.isAvailable));
        formData.append('isApproved', JSON.stringify(hotel.isApproved));
        return formData;
    }

}
