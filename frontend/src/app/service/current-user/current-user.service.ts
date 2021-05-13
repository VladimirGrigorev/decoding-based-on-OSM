import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../../model/user";

@Injectable({
  providedIn: 'root'
})
export class CurrentUserService {

  constructor(
    private http: HttpClient
  ) {
  }

  setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  setCurrentUser(user: User): void {
    localStorage.setItem('currentUser', JSON.stringify(user));
  }

  getCurrentUser(): User {
    return JSON.parse(<string>localStorage.getItem('currentUser')) as User;
  }

  getUpdatedCurrentUser(): User {
    this.setSession()
    return this.getCurrentUser();
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  public setSession() {

    this.http.get('/api/v1/user/profile', this.buildOpts())
      .subscribe(user => {
          this.setCurrentUser(user as User);
        },
        error => {
          console.log(error);
        });
  }

  public logout(){
    localStorage.removeItem('token');
    localStorage.removeItem('currentUser');
  }

  private buildOpts(): object {
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${this.getToken()}`
      })
    };
  }
}
