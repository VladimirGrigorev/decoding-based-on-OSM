import { Component, OnInit } from '@angular/core';
import {QuestionService} from "../../service/question/question.service";
import {CheckinService} from "../../service/checkin/checkin.service";
import {Checkin} from "../../model/checkin";

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  checkins: Array<Checkin> = [];

  constructor(
    private checkinService: CheckinService
  ) { }

  ngOnInit(): void {
    this.checkinService.findCheckins().subscribe(res => {
      this.checkins = res;
    })
  }

}
