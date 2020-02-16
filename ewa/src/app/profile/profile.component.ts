import {Component, OnInit} from '@angular/core';
import {ProfileController} from '../controller/profileController';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import {SessionService} from '../services/session.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  editState: number;
  newNameError: number;
  image: string;
  profileForm: FormGroup;

  constructor(private router: Router,
              private controller: ProfileController,
              private formBuilder: FormBuilder,
              private session: SessionService) { }

  ngOnInit() {
    this.editState = 0;
    this.newNameError = 0;
    this.image = "default";
    if (this.session.token == null){
      this.router.navigate(['']);
    }
    this.controller.getProfile();
    this.profileForm = this.formBuilder.group({
      avatar: [this.controller.getAvatar()],
      nickname: [this.controller.getNickname()],
      status: [this.controller.getProfileStatusBoolean()]
    });
  }

  edit() {
    this.editState = 1;
    this.profileForm = this.formBuilder.group({
      avatar: [this.controller.getAvatar()],
      nickname: [this.controller.getNickname()],
      status: [this.controller.getProfileStatusBoolean()]
    });
  }

  save() {
    const formdata: any = Object.assign(this.profileForm.value);
    if (formdata.nickname.length >= 4) {
      this.editState = 0;
      this.controller.setProfile(formdata.avatar, formdata.nickname, formdata.status);
      this.newNameError = 0;
    } else {
      this.newNameError = 1;
    }
  }

  discard() {
    this.editState = 0;
  }

  changeImage(event) {
    this.controller.setAvatar(event.srcElement.value);
  }

  gotoAdvanced(){
    this.router.navigate(['profile-advanced']);
  }
}
