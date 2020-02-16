import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import {ProfileController} from '../controller/profileController';
import {SessionService} from '../services/session.service';

@Component({
  selector: 'app-profile-advanced',
  templateUrl: './profile-advanced.component.html',
  styleUrls: ['./profile-advanced.component.css']
})
export class ProfileAdvancedComponent implements OnInit {
  editState: number;
  profileForm: FormGroup;

  emailError: boolean;
  usernameError: boolean;
  passwordError: boolean;

  constructor(private router: Router,
              private controller: ProfileController,
              private formBuilder: FormBuilder,
              private session: SessionService) { }

  ngOnInit() {
    this.editState = 0;
    this.emailError = false;
    this.usernameError = false;
    this.passwordError = false;

    if (this.session.token == null){
      this.router.navigate(['']);
    }
    this.controller.getProfileAdvanced();
    this.profileForm = this.formBuilder.group({
      email: [this.controller.email],
      username: [this.controller.username],
      password: [this.controller.password],
      password2: ['']
    });
  }

  edit() {
    this.editState = 1;
    this.emailError = false;
    this.usernameError = false;
    this.passwordError = false;

    this.profileForm = this.formBuilder.group({
      email: [this.controller.email],
      username: [this.controller.username],
      password: [this.controller.password],
      password2: ['']
    });
  }

  save() {
    const formdata: any = Object.assign(this.profileForm.value);
    this.controller.accountExist(formdata.email, formdata.username).subscribe(
      (res: any) => {
        res = res.Exist;
        console.log(res);
        this.emailError = res.email;
        this.usernameError = res.username;
        if (formdata.password !== formdata.password2){
          this.passwordError = true;
        }
        if (!this.emailError && !this.usernameError && !this.passwordError){
          this.controller.setProfileAdvanced(formdata.email, formdata.username, formdata.password);
          this.editState = 0;
        }
      }
    );
  }

  discard() {
    this.editState = 0;
  }

  gotoSimple(){
    this.router.navigate(['profile']);
  }
}
