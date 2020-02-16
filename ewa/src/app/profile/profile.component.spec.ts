import {async, ComponentFixture, TestBed, tick} from '@angular/core/testing';

import { ProfileComponent } from './profile.component';
import {inject, NO_ERRORS_SCHEMA} from '@angular/core';
import {RouterTestingModule} from '@angular/router/testing';
import {FormBuilder} from '@angular/forms';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {By} from '@angular/platform-browser';
import {ProfileController} from '../controller/profileController';

/**
 * @author Omer Erdem 500802898 IS203
 */

describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;
  let controller: ProfileController;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfileComponent ],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [FormBuilder],
      imports: [RouterTestingModule, HttpClientTestingModule]
    })
    .compileComponents();
  }));


  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should find form elements', () => {
    component.editState = 1;
    fixture.detectChanges();

    const avatarddl = fixture.debugElement.query(By.css('#avatarddl'));
    const nicknameinput = fixture.debugElement.query(By.css('#newName'));
    const statuscheckbox = fixture.debugElement.query(By.css('#statuscheckbox'));
    expect(avatarddl).toBeTruthy();
    expect(nicknameinput).toBeTruthy();
    expect(statuscheckbox).toBeTruthy();
  });

  it("should call edit()", (done) => {
    spyOn(component, 'edit');

    fixture.debugElement.query(By.css('#editbtn')).nativeElement.click();
    fixture.detectChanges();

    fixture.whenStable().then(() => {
      expect(component.edit).toHaveBeenCalled();
    });
    done();
  });

  it("should call gotoAdvanced()", (done) => {
    spyOn(component, 'gotoAdvanced');

    fixture.debugElement.query(By.css('#advancedbtn')).nativeElement.click();
    fixture.detectChanges();

    fixture.whenStable().then(() => {
      expect(component.gotoAdvanced).toHaveBeenCalled();
    });
    done();
  });

  it("should call discard()", (done) => {
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

  it("avatarddl should not be empty", (done) => {
    component.editState = 1;
    fixture.detectChanges();

    let avatarddl = fixture.debugElement.query(By.css('#avatarddl')).nativeElement;
    fixture.detectChanges();

    fixture.whenStable().then(() => {
      expect(avatarddl.value).not.toEqual(null);
      expect(avatarddl.value).not.toEqual("");
    });
    done();
  });

  it("avatarddl should select option 4", (done) => {
    component.editState = 1;
    fixture.detectChanges();

    let avatarddl = fixture.debugElement.query(By.css('#avatarddl')).nativeElement;
    avatarddl = avatarddl.options[4];
    fixture.detectChanges();

    fixture.whenStable().then(() => {
      expect(avatarddl.value).toBe("boy-4");
    });
    done();
  });
});
