import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ProfileAdvancedComponent} from './profile-advanced.component';
import {NO_ERRORS_SCHEMA} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';
import {By} from '@angular/platform-browser';
import {HttpClientTestingModule} from '@angular/common/http/testing';

/**
 * @author Omer Erdem 500802898 IS203
 */

describe('ProfileAdvancedComponent', () => {
  let component: ProfileAdvancedComponent;
  let fixture: ComponentFixture<ProfileAdvancedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ProfileAdvancedComponent],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [FormBuilder],
      imports: [RouterTestingModule, HttpClientTestingModule]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileAdvancedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should find form elements', () => {
    component.editState = 1;
    fixture.detectChanges();

    const emailinput = fixture.debugElement.query(By.css('#emailinput'));
    const usernameinput = fixture.debugElement.query(By.css('#usernameinput'));
    const passwordinput = fixture.debugElement.query(By.css('#passwordinput'));
    const passwordconfirminput = fixture.debugElement.query(By.css('#passwordconfirminput'));
    expect(emailinput).toBeTruthy();
    expect(usernameinput).toBeTruthy();
    expect(passwordinput).toBeTruthy();
    expect(passwordconfirminput).toBeTruthy();
  });

  it('should call edit()', (done) => {
    spyOn(component, 'edit');

    fixture.debugElement.query(By.css('#editbtn')).nativeElement.click();
    fixture.detectChanges();

    fixture.whenStable().then(() => {
      expect(component.edit).toHaveBeenCalled();
    });
    done();
  });

  it('should call gotoSimple()', (done) => {
    spyOn(component, 'gotoSimple');

    fixture.debugElement.query(By.css('#simplebtn')).nativeElement.click();
    fixture.detectChanges();

    fixture.whenStable().then(() => {
      expect(component.gotoSimple).toHaveBeenCalled();
    });
    done();
  });

  it('should call discard()', (done) => {
    spyOn(component, 'discard');

    component.editState = 1;
    fixture.detectChanges();

    fixture.debugElement.query(By.css('#discardbtn')).nativeElement.click();
    fixture.detectChanges();

    fixture.whenStable().then(() => {
      expect(component.discard).toHaveBeenCalled();
    });
    done();
  });
});
