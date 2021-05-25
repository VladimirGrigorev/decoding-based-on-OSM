import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './component/map/map.component';

import {LeafletModule} from '@asymmetrik/ngx-leaflet';
import { ProfileComponent } from './component/profile/profile.component';
import { HistoryComponent } from './component/history/history.component';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";
import { InterviewComponent } from './component/interview/interview.component';

@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    ProfileComponent,
    HistoryComponent,
    LoginComponent,
    RegisterComponent,
    InterviewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LeafletModule,
    FormsModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    BrowserAnimationsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
