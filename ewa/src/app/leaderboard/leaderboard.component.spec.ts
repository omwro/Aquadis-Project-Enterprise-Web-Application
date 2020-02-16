import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaderboardComponent } from './leaderboard.component';
import {NO_ERRORS_SCHEMA, ɵflushModuleScopingQueueAsMuchAsPossible} from '@angular/core';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {FormBuilder} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';
import {By} from '@angular/platform-browser';
import {User} from '../user';
import {element} from 'protractor';
import {assertNotNull} from '@angular/compiler/src/output/output_ast';


/**
 * @Author Danny Prodanovic
 */

fdescribe('LeaderboardComponent', () => {
  let component: LeaderboardComponent;
  let fixture: ComponentFixture<LeaderboardComponent>;
  let testUSers;


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeaderboardComponent ],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [HttpClient, HttpHandler, FormBuilder],
      imports: [RouterTestingModule]

    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeaderboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  /**
   * Shows at the top that the page is Leaderboard
   *
   * @Author Danny Prodanovic 500803288
   */
  it("shows leaderboard title", () => {
    const compiled = fixture.debugElement.query(By.css('.title')).nativeElement;
    expect(compiled).toBeTruthy();
  });

  /**
   * trophee of the players should be loaded. everyone is different of the others. only the last two are the same.
   * fake users are made for this test.
   *
   * @Author Danny Prodanovic 500803288
   */
  it("Trophee should be loaded",(done) => {
    component.users = [] = [
      {
        id: 1,
        email: "email",
        username: "username",
        password: "password",
        disabled: 0,
        nickname: "nickname",
        avatar: "man",
        score: 100,
        status: 0,
      },
      {
        id: 2,
        email: "email",
        username: "username",
        password: "password",
        disabled: 0,
        nickname: "nickname",
        avatar: "man",
        score: 80,
        status: 0,
      },
      {
        id: 3,
        email: "email",
        username: "username",
        password: "password",
        disabled: 0,
        nickname: "nickname",
        avatar: "man",
        score: 60,
        status: 0,
      }
      ,{
        id: 4,
        email: "email",
        username: "username",
        password: "password",
        disabled: 0,
        nickname: "nickname",
        avatar: "man",
        score: 40,
        status: 0,
      }
      ,{
        id: 5,
        email: "email",
        username: "username",
        password: "password",
        disabled: 0,
        nickname: "nickname",
        avatar: "man",
        score: 20,
        status: 0,
      }
    ];

    fixture.detectChanges();

    fixture.whenStable().then(() => {
      let images = fixture.debugElement.query(By.css('.award')).nativeElement;
      expect(images).toBeTruthy();
    });
    done();
  });

  /**
   * the amount of table rows should be 6. 1 for the description of the row and 5 players.
   * fake users are made for this test.
   *
   * @Author Danny Prodanovic
   */
  it('should test the table', (done)=>{
    expect(component.users).toBe(testUSers);
    component.users = [] = [
      {
        id: 1,
        email: "lol",
        username: "u1",
        password: "lol",
        disabled: 0,
        nickname: "lol",
        avatar: "lol",
        score: 22,
        status: 0,
      },
      {
        id: 2,
        email: "lol",
        username: "u2",
        password: "lol",
        disabled: 0,
        nickname: "lol",
        avatar: "lol",
        score: 32,
        status: 0,
      },
      {
        id: 3,
        email: "lol",
        username: "u3",
        password: "lol",
        disabled: 0,
        nickname: "lol",
        avatar: "lol",
        score: 42,
        status: 0,
      }
      ,{
        id: 4,
        email: "lol",
        username: "u4",
        password: "lol",
        disabled: 0,
        nickname: "lol",
        avatar: "lol",
        score: 52,
        status: 0,
      }
      ,{
        id: 5,
        email: "lol",
        username: "u5",
        password: "lol",
        disabled: 0,
        nickname: "lol",
        avatar: "lol",
        score: 62,
        status: 0,
      }
    ];

    fixture.whenStable().then(() => {
      fixture.detectChanges();
      let tableRows = fixture.nativeElement.querySelectorAll('tr');
      expect(tableRows).toBeTruthy();

    });
    done();
  });



});
