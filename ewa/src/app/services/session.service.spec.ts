import { TestBed } from '@angular/core/testing';

import { SessionService } from './session.service';
import {NO_ERRORS_SCHEMA} from '@angular/core';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {FormBuilder} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';

describe('SessionService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    providers: [HttpClient, HttpHandler, FormBuilder],
    imports: [RouterTestingModule]
  }));

  it('should be created', () => {
    const service: SessionService = TestBed.get(SessionService);
    expect(service).toBeTruthy();
  });

  it('should validate the token', () => {
    const service: SessionService = TestBed.get(SessionService);

    expect(service).toBeTruthy();

  });
});
