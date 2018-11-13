import {Injectable} from '@angular/core';
import {User} from '../../model/model.user';
import {AppComponent} from '../../app.component';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LocalStorageService, SessionStorageService} from "angular-web-storage";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private user: User;

  constructor(public local: LocalStorageService,
              public session: SessionStorageService,
              public http: HttpClient) {
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  // LOGIN //TODO ENCODE PSWD
  public logIn(user: User) {

    // creating base64 encoded String from user name and password
    const base64Credential: string = btoa(user.username + ':' + user.password);

    // Création des Headers et encapsulation pour requette HTTP
    const headers = new HttpHeaders({
      'Accept': 'application/json',
      'Authorization': 'Basic ' + base64Credential
    });
    const httpOptions = {headers};

    // DEBUG
    // console.log(httpOptions.headers.get('Authorization'));
    // console.log(httpOptions.headers.get('Accept'));

    return this.http.get(AppComponent.API_URL + '/auth/login', httpOptions);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  // LOGOUT
  logOut() {
    // remove user from Session storage to log user out
    this.session.clear();

    // DEBUG
    // console.log('ON LOG OUT ///////' + this.session.get(AppComponent.SESSION));

    return this.http.post(AppComponent.API_URL + '/auth/logout', {});
  }
}
