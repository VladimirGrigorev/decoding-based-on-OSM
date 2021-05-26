import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MapComponent} from "./component/map/map.component";
import {ProfileComponent} from "./component/profile/profile.component";
import {RegisterComponent} from "./component/register/register.component";
import {LoginComponent} from "./component/login/login.component";
import {InterviewComponent} from "./component/interview/interview.component";
import {HistoryComponent} from "./component/history/history.component";

const routes: Routes = [
  {path: '', component: MapComponent},
  {path: 'map', component: MapComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'interview', component: InterviewComponent},
  {path: 'history', component: HistoryComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
