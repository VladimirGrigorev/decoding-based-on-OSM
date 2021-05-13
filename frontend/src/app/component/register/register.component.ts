import { Component, OnInit } from '@angular/core';
import {SecurityService} from "../../service/security/security.service";
import {CurrentUserService} from "../../service/current-user/current-user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  isAuthenticated: boolean = false;
  login: string = '';
  password: string = '';

  constructor(
    private securityService: SecurityService,
    private currentUserService: CurrentUserService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.isAuthenticated = this.currentUserService.isAuthenticated();
  }

  onSubmit(login: string, password: string): void {
    this.securityService.register(login, password).subscribe(res => {
      this.router.navigate(['/login']);
    });
  }

}
