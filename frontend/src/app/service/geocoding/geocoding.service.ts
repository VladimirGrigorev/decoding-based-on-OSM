import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {GeocodingResult} from "../../model/geocoding-result";
import {CurrentUserService} from "../current-user/current-user.service";

@Injectable({
  providedIn: 'root'
})
export class GeocodingService {

  constructor(
    private http: HttpClient,
    private currentUserService: CurrentUserService
  ) {
  }

  findUserLocation(latitude: number, longitude: number, accuracy: number): Observable<GeocodingResult> {
    let body = {
      latitude,
      longitude,
      accuracy
    };

    return this.http.post<GeocodingResult>('api/v1/geocoding/find', body, this.buildOpts());
  }

  private buildOpts(): object {
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${this.currentUserService.getToken()}`
      })
    };
  }
}
