import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Hotel } from '../models/hotel';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HotelService {

  private host = environment.APIEnpointsURL;

  constructor(private http: HttpClient) { }

  public getHotels(): Observable<Hotel[]>{ return this.http.get<Hotel[]>(`${this.host}/api/hotels`); }

}
