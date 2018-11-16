import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AskForResetPasswordComponent } from './ask-for-reset-password.component';

describe('AskForResetPasswordComponent', () => {
  let component: AskForResetPasswordComponent;
  let fixture: ComponentFixture<AskForResetPasswordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AskForResetPasswordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AskForResetPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
