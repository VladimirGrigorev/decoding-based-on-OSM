import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {GeocodingResult} from "../../model/geocoding-result";

@Injectable({
  providedIn: 'root'
})
export class GeocodingService {

  constructor(
    private http: HttpClient
  ) {
  }

  findUserLocation(latitude: number, longitude: number, accuracy: number): Observable<GeocodingResult> {
    let body = {
      latitude,
      longitude,
      accuracy
    };

    return this.http.post<GeocodingResult>('api/v1/geocoding/find', body);
  }
}
