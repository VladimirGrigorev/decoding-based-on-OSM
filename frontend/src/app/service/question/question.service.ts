import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {CurrentUserService} from "../current-user/current-user.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Question} from "../../model/question";

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(
    private http: HttpClient,
    private currentUserService: CurrentUserService
  ) {
  }

  getQuestions(): Observable<Question[]> {
    return this.http.get<Question[]>(`api/v1/question`, this.buildOpts());
  }

  setUserQuestions(questions: Array<any>) {
    return this.http.post(`api/v1/question`, questions, this.buildOpts());
  }

  private buildOpts(): object {
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${this.currentUserService.getToken()}`
      })
    };
  }
}
