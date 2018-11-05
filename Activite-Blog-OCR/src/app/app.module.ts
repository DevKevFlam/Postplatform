import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
// import { AuthInterceptor } from './services/auth.interceptor';
import {AppComponent} from './app.component';
import {PostListComponent} from './post-list-component/post-list.component';
import {HeaderComponent} from './header/header.component';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PostService} from './services/post.service';
import {AuthGuardService} from './services/auth-guard.service';
import {AuthService} from './services/auth.service';
import {PostListItemComponent} from './post-list-item-component/post-list-item.component';
import {SigninComponent} from './auth/signin/signin.component';
import {SignupComponent} from './auth/signup/signup.component';
import { UpdatePostComponent } from './update-post/update-post.component';
import { NewPostComponent } from './new-post/new-post.component';
import { UserListComponent } from './user-list/user-list.component';
import { UserItemComponent } from './user-item/user-item.component';
import {UserService} from './services/user.service';


const appRoutes: Routes = [
  {path: 'posts', component: PostListComponent},
  {path: 'auth/signup', component: SignupComponent},
  {path: 'auth/signin', component: SigninComponent},
  {path: 'users', component: UserListComponent},
  {path: 'users/view/:id', component: UserItemComponent},
  {path: 'posts/view/:id', component: PostListItemComponent},
  {path: 'posts/new', canActivate: [AuthGuardService], component: NewPostComponent},
  {path: 'posts/update/:id', canActivate: [AuthGuardService], component: UpdatePostComponent},
  {path: '', redirectTo: 'posts', pathMatch: 'full'},
  {path: '**', redirectTo: 'posts'}
];

@NgModule({
  declarations: [
    AppComponent,
    PostListComponent,
    PostListItemComponent,
    HeaderComponent,
    SigninComponent,
    SignupComponent,
    UpdatePostComponent,
    NewPostComponent,
    UserListComponent,
    UserItemComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes),
  ],
  providers: [/*
    {
      provide : HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi   : true,
    },*/
    AuthService,
    AuthGuardService,
    PostService,
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
