import { Routes, RouterModule } from '@angular/router';
import {AuthGuardService} from './services/Auth/auth-guard.service';
import {LoginComponent} from "./component/Auth/login/login.component";
import {ProfileComponent} from "./component/User/profile/profile.component";
import {RegisterComponent} from "./component/Auth/register/register.component";
import {EmailValidationComponent} from './component/Auth/email-validation/email-validation.component';


const appRoutes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'verif/:token', component: EmailValidationComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ProfileComponent , canActivate: [AuthGuardService]},


  // otherwise redirect to profile
  { path: '**', redirectTo: '/login' }
];

export const routing = RouterModule.forRoot(appRoutes);
