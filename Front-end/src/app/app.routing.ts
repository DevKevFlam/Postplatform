import {RouterModule, Routes} from '@angular/router';
import {AuthGuardService} from './services/Auth/auth-guard.service';
import {LoginComponent} from './component/Auth/login/login.component';
import {ProfileComponent} from './component/User/profile/profile.component';
import {RegisterComponent} from './component/Auth/register/register.component';
import {EmailValidationComponent} from './component/Auth/email-validation/email-validation.component';
import {ResetPasswordComponent} from './component/Auth/reset-password/reset-password.component';
import {AskForResetPasswordComponent} from './component/Auth/ask-for-reset-password/ask-for-reset-password.component';
import {PostListComponent} from "./component/Post/post-list/post-list.component";
import {PostItemComponent} from "./component/Post/post-item/post-item.component";
import {NewPostComponent} from "./component/Post/new-post/new-post.component";
import {PostUpdateComponent} from "./component/Post/post-update/post-update.component";
import {UserItemComponent} from "./component/User/user-item/user-item.component";
import {UserListComponent} from "./component/User/user-list/user-list.component";

const appRoutes: Routes = [
  // Auth
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'verif/:token', component: EmailValidationComponent},

  // Reset Password
  {path: 'ResetPassword', component: AskForResetPasswordComponent},
  {path: 'ResetPassword/:token', component: ResetPasswordComponent},

  // Posts
  {path: 'posts', component: PostListComponent},
  {path: 'posts/view/:id', component: PostItemComponent},
  {path: 'posts/new', canActivate: [AuthGuardService], component: NewPostComponent},
  {path: 'posts/update/:id', canActivate: [AuthGuardService], component: PostUpdateComponent},

  // Users
  {path: 'users', component: UserListComponent},
  {path: 'users/view/:id', component: UserItemComponent},

  // Current User
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuardService]},

  // otherwise redirect to profile
  {path: '**', redirectTo: '/login'},
  {path: '', redirectTo: 'posts', pathMatch: 'full'},

];

export const routing = RouterModule.forRoot(appRoutes);
