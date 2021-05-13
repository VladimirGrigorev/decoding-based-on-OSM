import { Component } from '@angular/core';
import {CurrentUserService} from "./service/current-user/current-user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';

  constructor(
    private currentUserService: CurrentUserService
  ) {
  }

  isAuthenticated(): boolean{
    return this.currentUserService.isAuthenticated();
  }

  logout() {
    this.currentUserService.logout();
    location.reload();
  }
}
