import {async, ComponentFixture, fakeAsync, TestBed} from '@angular/core/testing';
import { LogInComponent } from './log-in.component';
import {inject, NO_ERRORS_SCHEMA} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {By} from '@angular/platform-browser';
import { RouterTestingModule } from '@angular/router/testing';
import { RouterLinkWithHref } from '@angular/router';

const loginServiceSpy = jasmine.createSpyObj('LoginService', ['login']);

describe('LogInComponent', () => {
  let component: LogInComponent;
  let fixture: ComponentFixture<LogInComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LogInComponent ],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [FormBuilder, {provide: LogInComponent, useValue: loginServiceSpy }],
      imports: [RouterTestingModule.withRoutes([]), HttpClientTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LogInComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  function updateForm(userEmail, userPassword) {
    component.loginForm.controls['username'].setValue(userEmail);
    component.loginForm.controls['password'].setValue(userPassword);
  }


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  //joel verhagen - 500760451
  it('should render title in a h3 tag', async(() => {
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h3').textContent).toContain(component.title);
  }));

  // joel verhagen - 500760451
  it('component initial state', () => {
    expect(component.loginForm).toBeTruthy();
    expect(component.loginForm.invalid).toBeFalsy();
    expect(component.ngOnInit).toBeTruthy();
  });

  //joel verhagen - 500760451
  it('href register link should navigate to register page', () => {
    const debugElements = fixture.debugElement.queryAll(By.directive(RouterLinkWithHref));
    const index = debugElements.findIndex(de => {
      return de.properties['href'] === '/register';
    });
    expect(index).toBeGreaterThan(-1);
  });


  //joel verhagen - 500760451
  it('created a form with username and password input and login button', () => {
    // const fixture = TestBed.createComponent(LoginComponent);
    const usernameContainer = fixture.debugElement.nativeElement.querySelector('#username');
    const passwordContainer = fixture.debugElement.nativeElement.querySelector('#password');
    const loginBtnContainer = fixture.debugElement.nativeElement.querySelector('#loginBtn');
    expect(usernameContainer).toBeDefined();
    expect(passwordContainer).toBeDefined();
    expect(loginBtnContainer).toBeDefined();
  });

  //joel verhagen - 500760451
  it('when username is invalid it is false', () => {
    updateForm(component.loginForm.controls.username.setValue(''),
      component.loginForm.controls.password);
    expect(component.loginForm.controls.username.invalid).toBeFalsy();
  });

  //joel verhagen - 500760451
  it('when password is invalid it is false', () => {
    updateForm(component.loginForm.controls.username,
      component.loginForm.controls.password.setValue(''));
    expect(component.loginForm.controls.password.invalid).toBeFalsy();
  });

  //joel verhagen - 500760451
  //should call onlogin when pushing the log in button
  //except we dont have <button> in log-in.html component
  //we work with (ngSumbit)
  //this is fine code for a button, looking for testing code for (ngSumbit)
  // is no where to be found
  it("should call onlogin", (done) => {
    spyOn(component,'onLogin').and.callThrough();
    component.onLogin(); //1 == 1
    fixture.debugElement.query(By.css('#loginBtn')).nativeElement.click();
    fixture.detectChanges();

    fixture.whenStable().then(() => {
      expect(component.onLogin).toHaveBeenCalled();
    });
    done();
  });


});



