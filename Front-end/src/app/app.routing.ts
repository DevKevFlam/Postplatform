import {RouterModule, Routes} from '@angular/router';
import {AuthGuardService} from './services/Auth/auth-guard.service';
import {LoginComponent} from './component/Auth/login/login.component';
import {ProfileComponent} from './component/User/profile/profile.component';
import {RegisterComponent} from './component/Auth/register/register.component';
import {EmailValidationComponent} from './component/Auth/email-validation/email-validation.component';
import {ResetPasswordComponent} from './component/Auth/reset-password/reset-password.component';
import {AskForResetPasswordComponent} from './component/Auth/ask-for-reset-password/ask-for-reset-password.component';


const appRoutes: Routes = [
  {path: 'register', component: RegisterComponent},
  {path: 'verif/:token', component: EmailValidationComponent},
  {path: 'ResetPassword/:token', component: ResetPasswordComponent},
  {path: 'ResetPassword', component: AskForResetPasswordComponent},
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuardService]},


  // otherwise redirect to profile
  {path: '**', redirectTo: '/login'}
];

export const routing = RouterModule.forRoot(appRoutes);
