import {Injectable} from '@angular/core';
import {User} from '../../model/model.user';
import {AppComponent} from '../../app.component';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RegistationService {

  errorMessage: string;

  constructor(public http: HttpClient) {
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  // SIGNUP // OK
  createAccount(user: User) {
    return this.http.post(AppComponent.API_URL + '/auth/register', user);
  }

  verifyMail(token: string) {

    return this.http.get(AppComponent.API_URL + '/auth/Enabled/' + token);

  }

  askForResetPassword(username: string){

    return this.http.get(AppComponent.API_URL + '/ResetPassword/' + username)

  }

  resetPassword(token: string, psw: string) {
    let user:User;

    this.http.post(AppComponent.API_URL + '/auth/getUser', token).subscribe(
      (data:User) => {
        user = data;
        user.password = psw;
      },
      err => {
        console.log(err);
        this.errorMessage = 'username not found';
      }
    );

    return this.http.post(AppComponent.API_URL + '/auth/ResetPassword/' + token, user);

  }


}
