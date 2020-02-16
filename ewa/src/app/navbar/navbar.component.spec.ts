import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import { NavbarComponent } from './navbar.component';
import {ElementRef, NO_ERRORS_SCHEMA} from '@angular/core';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {FormBuilder} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';
import {Router} from '@angular/router';
import {AppComponent} from '../app.component';
import * as path from 'path';
import {Test} from 'tslint';
import {Location} from '@angular/common';
import {By} from '@angular/platform-browser';
import {ProfileComponent} from '../profile/profile.component';

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;
  let router: Router;
  let location: Location;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavbarComponent, AppComponent ],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [HttpClient, HttpHandler, FormBuilder],
      imports: [RouterTestingModule.withRoutes([{ path: '', component: NavbarComponent}])]
    })
    .compileComponents();

  }));

  beforeEach(() => {
    router = TestBed.get(Router);
    location = TestBed.get(Location);
    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    fixture.autoDetectChanges()
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
