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
import { ResetPasswordComponent } from './component/Auth/reset-password/reset-password.component';
import { AskForResetPasswordComponent } from './component/Auth/ask-for-reset-password/ask-for-reset-password.component';
import { PostItemComponent } from './component/Post/post-item/post-item.component';
import { PostListComponent } from './component/Post/post-list/post-list.component';
import { NewPostComponent } from './component/Post/new-post/new-post.component';
import {PostService} from "./services/posts.service";
import { NavBarComponent } from './component/Header/nav-bar/nav-bar.component';
import { UserItemComponent } from './component/User/user-item/user-item.component';
import { UserListComponent } from './component/User/user-list/user-list.component';
import { PostUpdateComponent } from './component/Post/post-update/post-update.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    EmailValidationComponent,
    ResetPasswordComponent,
    AskForResetPasswordComponent,
    PostItemComponent,
    PostListComponent,
    NewPostComponent,
    NavBarComponent,
    UserItemComponent,
    UserListComponent,
    PostUpdateComponent
  ],

  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    routing,
    AngularWebStorageModule
  ],
  providers: [AuthService, RegistationService , AuthGuardService, PostService],
  bootstrap: [AppComponent]
})
export class AppModule { }
