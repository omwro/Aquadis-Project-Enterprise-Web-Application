import {HttpClient, HttpParams} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Injectable} from '@angular/core';
import {SessionService} from '../services/session.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileController {
  // Profile
  private avatar: string;
  private nickname: string;
  private score: number;
  private profileStatus: number;

  // Profile Advanced
  private _email: string;
  private _username: string;
  private _password: string;

  constructor(private http: HttpClient,
              private router: Router,
              private route: ActivatedRoute,
              private session: SessionService) {}

  getProfile(){
    this.http.get("/api/profile/" + this.session.token).subscribe(
      (result: any) => {
        result = result.Profile;
        console.log("Get Profile:", result);
        this.avatar = result.avatar;
        this.nickname = result.nickname;
        this.score = result.score;
        this.profileStatus = result.status;
      }
    )
  }

  setProfile(avatar: string, nickname: string, status: boolean) {
    let privacy = 0;
    if (status) {
      privacy = 1;
    } else {
      privacy = 0;
    }
    let body: any = {
      "avatar": avatar,
      "nickname": nickname,
      "status": privacy
    };
    this.http.put('/api/profile/'+this.session.token, body).subscribe(
      (result: any) => {
        result = result.Profile;
        console.log("Update Profile:", result);
        this.avatar = result.avatar;
        this.nickname = result.nickname;
        this.profileStatus = result.status;
      }, error => {
        console.log("Error:", error);
      }
    );
  }

  getProfileAdvanced(){
    this.http.get("/api/profile-advanced/" + this.session.token).subscribe(
      (result: any) => {
        result = result.Advanced;
        console.log("Get Profile Advanced:", result);
        this._email = result.email;
        this._username = result.username;
        this._password = result.password;
      }
    )
  }

  setProfileAdvanced(email: string, username: string, password: string) {
    let body: any = {
      "email": email,
      "username": username,
      "password": password
    };
    this.http.put('/api/profile-advanced/'+this.session.token, body).subscribe(
      (result: any) => {
        result = result.Advanced;
        console.log("Update Profile Advanced:", result);
        this._email = result.email;
        this._username = result.username;
        this._password = result.password;
      }, error => {
        console.log("Error:", error);
      }
    );
  }

  accountExist(email: string, username: string): Observable<Object>{
    return this.http.get("/api/exist/"+email+"/"+username+"/"+this.session.token);
  }

  getAvatar() {
    return this.avatar;
  }
  setAvatar(avatar: string) {
    this.avatar = avatar;
  }

  getNickname() {
    return this.nickname;
  }
  setNickname(nickname: string) {
    this.nickname = nickname;
  }

  getScore() {
    return this.score;
  }
  setScore(score: number) {
    this.score = score;
  }

  getProfileStatusBoolean() {
    return this.profileStatus !== 0;
  }
  getProfileStatus() {
    if (this.profileStatus === 0) {
      return 'PUBLIC';
    } else if (this.profileStatus === 1) {
      return 'PRIVATE';
    } else {
      return 'SOMETHING WENT WRONG';
    }
  }
  setProfileStatus(status: number) {
    this.profileStatus = status;
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }

  get username(): string {
    return this._username;
  }

  set username(value: string) {
    this._username = value;
  }

  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
  }

  get passwordEnc(){
    var length = String(this.password).length;
    var enc = "";
    for (let i = 0; i < length; i++) {
      enc += "•";
    }
    return enc;
  }

  addScore(score: number) {
    let body: any = {
      "score": score
    };
    this.http.put('/api/profile/addscore/' + this.session.token, body).subscribe(
      (result: number) => {
        this.score = result;
      }, error => {
        console.log("Error:", error);
      });
  }
}
