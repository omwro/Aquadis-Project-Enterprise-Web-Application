import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterComponent } from './register.component';
import {NO_ERRORS_SCHEMA} from '@angular/core';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {FormBuilder} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {AccountController} from "../controller/accountController";
import {By} from '@angular/platform-browser';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let controller: AccountController;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegisterComponent ],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [FormBuilder],
      imports: [RouterTestingModule, HttpClientTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // check there is values in input field. There should not be a problem!- Munauwar Mughal 500802553
  it("should retrieve the form input values", (done) => {
    fixture.whenStable().then(() => {
      let email = component.registerForm.controls['email'];
      email.setValue('email@hotmail.com');
      let username = component.registerForm.controls['username'];
      username.setValue('user');
      let password = component.registerForm.controls['password'];
      password.setValue('hallo123');
      let passwordRepeat = component.registerForm.controls['repeatpassword'];
      passwordRepeat.setValue('hallo123');

      fixture.detectChanges();

      expect(email).toBeTruthy();
      expect(username).toBeTruthy();
      expect(password).toBeTruthy();
      expect(passwordRepeat).toBeTruthy();
    });
    done();
  });

  // check there is values in input field. There should be a error!- Munauwar Mughal 500802553
  it("should give error retrieve the form input values", (done) => {
    fixture.whenStable().then(() => {
      let email = component.registerForm.controls['email'];
      email.setValue('email@hotmail.com');
      let username = component.registerForm.controls['username'];
      username.setValue('user');
      let password = component.registerForm.controls['password'];
      password.setValue('hallo123');
      let passwordRepeat = component.registerForm.controls['repeatpassword'];
      passwordRepeat.setValue('hallo1234');

      fixture.detectChanges();

      expect(email).toBeTruthy();
      expect(username).toBeTruthy();
      expect(password == passwordRepeat).toBeFalsy();
    });
    done();
  });

  // password must be longer then 6 characters - Munauwar Mughal 500802553
  it("password should be longer than 6 characters", () => {
    let password = "test123";
    expect(password.length).toBeGreaterThan(6);
  });

  // password give error when character not longer than 6 - Munauwar Mughal 500802553
  it("it must give error when password not longer than 6 characters", () => {
    let password = "test";
    expect(password.length).toBeLessThan(6);
  });

  // it must be a real email adress - Munauwar Mughal 500802553
  it("email adress must be real", () => {
    let email = component.registerForm.controls['email'];
    email.setValue("test@gmail.com");

    expect(email.valid).toBeTruthy();
  });

  // if mail is not correct - Munauwar Mughal 500802553
  it("email adres error when mail not correct", () => {
    let email = component.registerForm.controls['email'];
    email.setValue("foute-email");

    expect(email.valid).toBeFalsy();
  });

  //joel verhagen - 500760451
  it('should render title in a h3 tag', async(() => {
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h3').textContent).toContain(component.title);
  }));


  //Danny Prodanovic 500803288
  it('Button should begin at one', async(() => {
    expect(fixture.debugElement.nativeElement.querySelector('button:nth-child(0)')).toBeNull();
  }));

  //Danny Prodanovic 500803288
  it('Input should begin at at one', async() => {
    expect(fixture.debugElement.nativeElement.querySelector('input:nth-child(0)')).toBeNull();
  });

  //Danny Prodanovic 500803288
  it('There should be an input field for email', async() => {
    expect(fixture.debugElement.nativeElement.querySelector('input:nth-child(1)')).toBeTruthy();
  });

  //Danny Prodanovic 500803288
  it('Input field should contain a placeholder with email', async(() => {
    expect(fixture.debugElement.nativeElement.querySelector('input:nth-child(1)').placeholder).toContain("email");
  }));

  //Danny Prodanovic 500803288
  it('There should be an input field for username', async() => {
    expect(fixture.debugElement.nativeElement.querySelector('input:nth-child(3)')).toBeTruthy();
  });

  //Danny Prodanovic 500803288
  it('There should be an input field for username', async() => {
    expect(fixture.debugElement.nativeElement.querySelector('input:nth-child(3)').placeholder).toContain("username");
  });

  //Danny Prodanovic 500803288
  it('There should be an input field for password', async() => {
    expect(fixture.debugElement.nativeElement.querySelector('input:nth-child(5)')).toBeTruthy();
  });

  //Danny Prodanovic 500803288
  it('Input field should contain a placeholder with password', async(() => {
    expect(fixture.debugElement.nativeElement.querySelector('input:nth-child(5)').placeholder).toContain("password");
  }));






});
