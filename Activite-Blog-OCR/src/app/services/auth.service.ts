import {Injectable} from '@angular/core';
import {UserService} from '../services/user.service';
import {UserDto} from "../models/userDto.model";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Post} from "../models/post.model";
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  users: UserDto [] = [];
  usersDtoSubject = new Subject<UserDto[]>();

  private apiUrl: String = 'http://localhost:9001';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };

  // TODO liaison avec Spring Security et Service Auth from PostPlatform BAckend

  constructor(private serviceUser: UserService, private http: HttpClient) {

  }

  private registerUserAccount(user: UserDto) {
    const objectObservable = this.http.post(this.apiUrl + '/user/registration', user, this.httpOptions).pipe();
    return objectObservable;
  }

  createNewUser(user: UserDto) {

    return new Promise(

        this.registerUserAccount(user).subscribe );
  }

  signInUser(email: string, password: string) {

    return new Promise(
      (resolve, reject) => {
        firebase.auth().signInWithEmailAndPassword(email, password).then(
          () => {
            resolve();
          },
          (error) => {
            reject(error);
          }
        );
      }
    );
  }

  signOutUser() {
    /* firebase.auth().signOut();*/
  }
}
