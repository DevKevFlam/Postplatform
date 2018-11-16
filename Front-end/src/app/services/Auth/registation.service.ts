import {Injectable} from '@angular/core';
import {User} from '../../model/model.user';
import {AppComponent} from '../../app.component';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RegistationService {

  constructor(public http: HttpClient) { }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  // SIGNUP // OK
  createAccount(user: User) {
    return this.http.post(AppComponent.API_URL + '/auth/register', user);
   }

   verifyMail(token: string){

    return this.http.get(AppComponent.API_URL + '/auth/Enabled/' + token);

   }
}
