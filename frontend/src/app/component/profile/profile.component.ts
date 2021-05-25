import {Component, OnDestroy, OnInit} from '@angular/core';
import {User} from "../../model/user";
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {SecurityService} from "../../service/security/security.service";
import {CurrentUserService} from "../../service/current-user/current-user.service";
import {first} from "rxjs/operators";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  username: string | undefined;
  userInfo: User = {} as User;
  private sub: Subscription | undefined;
  isAdmin: boolean = false;
  user: User = {} as User;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private currentUserService: CurrentUserService
  ) { }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.loadUserProfile();
    });
  }

  loadUserProfile() {
    this.user = this.currentUserService.getCurrentUser();
  }
}
