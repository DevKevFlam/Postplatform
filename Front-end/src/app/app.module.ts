import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './component/Auth/login/login.component';
import { RegisterComponent } from './component/Auth/register/register.component';
import { ProfileComponent } from './component/User/profile/profile.component';
import {FormsModule} from "@angular/forms";
import {AuthService} from "./services/Auth/auth.service";
import {RegistationService} from "./services/Auth/registation.service";
import {AuthGuardService} from "./services/Auth/auth-guard.service";
import {routing} from "./app.routing";
import {HttpClientModule} from "@angular/common/http";
import {AngularWebStorageModule} from "angular-web-storage";
import {EmailValidationComponent} from './component/Auth/email-validation/email-validation.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    EmailValidationComponent
  ],

  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    routing,
    AngularWebStorageModule
  ],
  providers: [AuthService, RegistationService , AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
