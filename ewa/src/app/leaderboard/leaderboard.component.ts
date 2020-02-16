import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../user';

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.css']
})
export class LeaderboardComponent implements OnInit {

  users: User[];
  private usersUrl: string;
  testUSers;

  constructor(private http: HttpClient) {
    this.usersUrl = '/api/users/top5users';
  }

  public getTopUsers() {
    return this.http.get(this.usersUrl);
  }


  ngOnInit() {

    this.getTopUsers().subscribe(
      (result: any) => {
      this.users = result;
    },
      error => {
        console.log("Error:",error);
      });
  }
}


