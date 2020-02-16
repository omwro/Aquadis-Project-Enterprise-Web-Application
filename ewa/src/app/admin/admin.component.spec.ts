import {async, ComponentFixture, fakeAsync, TestBed} from '@angular/core/testing';

import { AdminComponent } from './admin.component';
import {NO_ERRORS_SCHEMA} from '@angular/core';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {FormBuilder} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';
import {NgxPaginationModule} from 'ngx-pagination';
import {By} from "@angular/platform-browser";
import {of} from "rxjs";
import {User} from "../user";

/*
  @author: Munauwar Mughal 500802553
 */

describe('AdminComponent', () => {
  let component: AdminComponent;
  let fixture: ComponentFixture<AdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminComponent ],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [HttpClient, HttpHandler, FormBuilder],
      imports: [RouterTestingModule, NgxPaginationModule],

    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  //checks if there is title admin dashboard - Munauwar Mughal 500802553
  it("shows dashboard title", async(() => {
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h4').textContent).toContain('Admin dashboard');
  }));

  //toggle between classes - Munauwar Mughal 500802553
  it("should toggle state", (done) => {
    spyOn(component, 'toggleState');

    let button = fixture.debugElement.nativeElement.querySelector('button');
    button.click();

    // checks if button is called with the function
    fixture.whenStable().then(() => {
      expect(component.toggleState).toHaveBeenCalled();
    });
    done();
  });

  //Enabled status text - Munauwar Mughal 500802553
  it("should show enabled user/message", () => {
    spyOn(component, "getStatusText").and.callThrough();
    expect(component.getStatusText(0)).toEqual("Enabled");
  });

  //Disabled status text - Munauwar Mughal 500802553
  it("should show disabled user/message", () => {
    spyOn(component, "getStatusText").and.callThrough();
    expect(component.getStatusText(1)).toEqual("Disabled");
  });

  //Tycho Scholtze - 500807273
  it('Buttons should start at one', async(() => {
    expect(fixture.debugElement.nativeElement.querySelector('button:nth-child(0)')).toBeNull();
  }));

  //Tycho Scholtze - 500807273
  it('accounts should have a button on the position for users', async(() => {
    expect(fixture.debugElement.nativeElement.querySelector('button:nth-child(1)')).toBeTruthy();
  }));

  //Tycho Scholtze - 500807273
  it('accounts should have a button on the position for messages', async(() => {
    expect(fixture.debugElement.nativeElement.querySelector('button:nth-child(2)')).toBeTruthy();
  }));

  //Tycho Scholtze - 500807273
  it('accounts should have a button on the position for map', async(() => {
    expect(fixture.debugElement.nativeElement.querySelector('button:nth-child(3)')).toBeTruthy();
  }));

  //Tycho Scholtze - 500807273
  it('There should be no more than three buttons on the account page', async(() => {
    expect(fixture.debugElement.nativeElement.querySelector('button:nth-child(4)')).toBeNull();
  }));

  //Tycho Scholtze - 500807273
  it('The users button should contain the correct text', async(() => {
    expect(fixture.debugElement.nativeElement.querySelector('button:nth-child(1)').textContent).toContain("Users");
  }));

  //Tycho Scholtze - 500807273
  it('The messages button should contain the correct text', async(() => {
    expect(fixture.debugElement.nativeElement.querySelector('button:nth-child(2)').textContent).toContain("Messages");
  }));

  //Tycho Scholtze - 500807273
  it('The map button should contain the correct text', async(() => {
    expect(fixture.debugElement.nativeElement.querySelector('button:nth-child(3)').textContent).toContain("Map");
  }));

  //Tycho Scholtze - 500807273
  it('The message button should be clickable', async(() => {
    // Used for checking if the page is stable.
    spyOn(component, 'toggleState');

    // Click on button 2.
    fixture.debugElement.nativeElement.querySelector('button:nth-child(2)').click();

    // Wait until the change has occured in the DOM.
    fixture.whenStable().then(() => {
      // Check if it correctly happened.
      expect(component.toggleState).toHaveBeenCalled();
    });
  }));

  //Tycho Scholtze - 500807273
  it('The map button should be clickable', async(() => {
    // Used for checking if the page is stable.
    spyOn(component, 'toggleState');

    // Click on button 3.
    fixture.debugElement.nativeElement.querySelector('button:nth-child(3)').click();

    // Wait until the change has occured in the DOM.
    fixture.whenStable().then(() => {
      // Check if it correctly happened.
      expect(component.toggleState).toHaveBeenCalled();
    });
  }));
});
