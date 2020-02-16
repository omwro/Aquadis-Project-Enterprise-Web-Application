import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AccountController} from '../controller/accountController';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  private _title: String;

  constructor(private formBuilder: FormBuilder,
              private controller: AccountController) { }

  ngOnInit() {
    this._title = "Register";
    this.registerForm = this.formBuilder.group({
      email: ['', Validators.email],
      username: [''],
      password: [''],
      repeatpassword: ['']
    });
  }

  onSubmit() {
    const formdata: any = Object.assign(this.registerForm.value);
    
    this.controller.register(formdata.email, formdata.username, formdata.password, formdata.repeatpassword);
  }


  get title(): String {
    return this._title;
  }
}
