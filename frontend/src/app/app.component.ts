import { Component } from '@angular/core';
import {CurrentUserService} from "./service/current-user/current-user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';

  constructor(
    private currentUserService: CurrentUserService,
    private router: Router
  ) {
  }

  isAuthenticated(): boolean{
    return this.currentUserService.isAuthenticated();
  }

  logout() {
    this.currentUserService.logout();
    this.router.navigate(['']);
  }

  isUser(): boolean{
    if(this.currentUserService.isAuthenticated())
      return !!this.currentUserService.getCurrentUser().roles.find(role => role.name == "ROLE_USER");
    else
      return false;
  }
}
