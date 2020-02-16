import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Injectable} from '@angular/core';
import {ProfileController} from './profileController';
import {SessionService} from '../services/session.service';

@Injectable({
  providedIn: 'root'
})
export class AccountController {
  constructor(private http: HttpClient,
              private router: Router,
              private route: ActivatedRoute,
              private profileController: ProfileController,
              private session: SessionService) {
  }

  login(name: string, password: string) {
    this.http.get('/api/login/' + name + '/' + password).subscribe(
      (result: any) => {
        console.log('Login result', result);
        if (result != null) {
          console.log('Login Success');
          result = result.Session;

          this.session.token = result.token;

          this.profileController.setAvatar(result.avatar);
          this.profileController.setNickname(result.nickname);
          this.profileController.setScore(result.score);
          this.profileController.setProfileStatus(result.status);

          if(result.userType == "User"){
            this.router.navigate(['mainpage']);
          } else if (result.userType == "Admin"){
            this.router.navigate(['admin']);
          }
        } else {
          console.log('Login Failed');
        }
      });
  }

  register(email: string, username: string, password: string, passwordRepeat: string) {
    let body: any = {
      'email': email,
      'username': username,
      'password': password,
      'passwordRepeat': passwordRepeat
    };

    this.http.post('/api/users', body).subscribe(
      (result: any) => {
        if(password == passwordRepeat) {
          if(password.length > 6) {
            if (result != null){
              result = result.Session;
              this.session.token = result.token;
              this.profileController.setAvatar(result.avatar);
              this.profileController.setNickname(result.nickname);
              this.profileController.setScore(result.score);
              this.profileController.setProfileStatus(result.status);
              this.router.navigate(['mainpage']);
            }
          } else {
            alert("password length must be greater then 6 characters!");
          }
        } else {
          alert("password is not the same");
          console.log('Register Failed');
        }
      }, error => {
        console.log(error);
      });
  };
}
