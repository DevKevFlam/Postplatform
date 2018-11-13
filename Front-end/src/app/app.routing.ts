import { Routes, RouterModule } from '@angular/router';
import {AuthGuardService} from './services/Auth/auth-guard.service';
import {LoginComponent} from "./component/Auth/login/login.component";
import {ProfileComponent} from "./component/User/profile/profile.component";
import {RegisterComponent} from "./component/Auth/register/register.component";


const appRoutes: Routes = [
  { path: 'profile', component: ProfileComponent , canActivate: [AuthGuardService]},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // otherwise redirect to profile
   { path: '**', redirectTo: '/login' }
];

export const routing = RouterModule.forRoot(appRoutes);
