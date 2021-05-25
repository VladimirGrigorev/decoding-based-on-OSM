import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, FormControl, Validators } from '@angular/forms';
import {QuestionService} from "../../service/question/question.service";
import {Question} from "../../model/question";

@Component({
  selector: 'app-interview',
  templateUrl: './interview.component.html',
  styleUrls: ['./interview.component.css']
})
export class InterviewComponent implements OnInit {

  names: Array<string> = [];
  values: Array<string> = [];
  name: string | undefined;
  value: string | undefined;
  form: FormGroup;

  constructor(
    private questionService: QuestionService,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      checkArray: this.fb.array([])
    })
  }

  ngOnInit(): void {
    this.questionService.getQuestions().subscribe(res => {
      var i:number;
      for(i = 0; i < res.length; i++) {
        this.name = res[i].text;
        this.value = res[i].text;
        this.names.push(this.name);
        this.values.push(this.value);
      }
    });
  };

  onCheckboxChange(e: Event) {
    const checkArray: FormArray = this.form.get('checkArray') as FormArray;

    // @ts-ignore
    if (e.target.checked) {
      // @ts-ignore
      checkArray.push(new FormControl(e.target.value));
    } else {
      let i: number = 0;
      // @ts-ignore
      checkArray.controls.forEach((item: FormControl) => {
        // @ts-ignore
        if (item.value == e.target.value) {
          checkArray.removeAt(i);
          return;
        }
        i++;
      });
    }
  }

  submitForm() {
    var data = this.form.value;
    this.questionService.setUserQuestions(data.checkArray).subscribe(res=>{
      console.log(this.form.value);
    });
  }

}
