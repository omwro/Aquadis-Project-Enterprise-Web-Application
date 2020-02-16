import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor(private http: HttpClient) { }

  get token(): string {
    return sessionStorage.getItem("token");
  }

  set token(value: string) {
    if (value == null || value == "null"){
      console.log(this.token);
      this.http.get("/api/logout/"+this.token).subscribe(
        (result) => {
          console.log('loggedout()');
        }
      );
    }
    sessionStorage.setItem("token", value);
  }

  validate(){
    if (this.token != null && this.token != "null"){
      return true;
    } else {
      return false
    }
  }
}
