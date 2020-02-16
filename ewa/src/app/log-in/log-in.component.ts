import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AccountController} from '../controller/accountController';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})
export class LogInComponent implements OnInit {

  loginForm: FormGroup;
  private _title: String = "Sign in";

  constructor(
      private _formBuilder: FormBuilder,
      private controller: AccountController
  ) { }


  ngOnInit() {
    this.loginForm = this._formBuilder.group({
      username: [''],
      password: ['']
    });


  }
  onLogin() {
    const formdata: any = Object.assign(this.loginForm.value);
    this.controller.login(formdata.username, formdata.password);
  }


  get title(): String {
    return this._title;
  }
}
