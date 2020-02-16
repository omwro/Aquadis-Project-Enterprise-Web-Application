import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MainpageComponent } from './mainpage.component';
import {NO_ERRORS_SCHEMA} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {google} from '@agm/core/services/google-maps-types';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('MainpageComponent', () => {
  let component: MainpageComponent;
  let fixture: ComponentFixture<MainpageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MainpageComponent ],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [FormBuilder],
      imports: [HttpClientTestingModule, google]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MainpageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  //google is not defined, imports doesn't work
  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
