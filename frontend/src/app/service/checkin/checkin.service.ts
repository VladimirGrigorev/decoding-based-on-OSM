import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CurrentUserService} from "../current-user/current-user.service";
import {Observable} from "rxjs";
import {GeocodingResult} from "../../model/geocoding-result";
import {Checkin} from "../../model/checkin";

@Injectable({
  providedIn: 'root'
})
export class CheckinService {

  constructor(
    private http: HttpClient,
    private currentUserService: CurrentUserService
  ) {
  }

  findCheckins(): Observable<Checkin[]> {
    return this.http.get<Checkin[]>(`api/v1/checkin`, this.buildOpts());
  }

  createCheckin(locationName: string) {
    return this.http.post(`api/v1/checkin/create`, locationName, this.buildOpts());
  }

  private buildOpts(): object {
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${this.currentUserService.getToken()}`
      })
    };
  }
}
