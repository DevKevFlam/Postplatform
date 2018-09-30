import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {PostListComponent} from './post-list-component/post-list.component';
import {HeaderComponent} from './header/header.component';
import {RouterModule, Routes} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PostService} from './services/post.service';
import {AuthGuardService} from './services/auth-guard.service';
import {AuthService} from './services/auth.service';
import {PostListItemComponent} from './post-list-item-component/post-list-item.component';
import {SigninComponent} from './auth/signin/signin.component';
import {SignupComponent} from './auth/signup/signup.component';
import { UpdatePostComponent } from './update-post/update-post.component';
import { NewPostComponent } from './new-post/new-post.component';


const appRoutes: Routes = [
  {path: 'auth/signup', component: SignupComponent},
  {path: 'auth/signin', component: SigninComponent},
  {path: 'posts', component: PostListComponent},
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
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
  ],
  providers: [
    AuthService,
    AuthGuardService,
    PostService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
