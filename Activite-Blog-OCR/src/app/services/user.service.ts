import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import * as firebase from 'firebase';
import {User} from '../models/user.model';
import {HttpClient} from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:9001';
  users: User[] = [];

  userSubject = new Subject<User[]>();

  constructor(private http: HttpClient) {

  }

  emitUsers() {
    this.userSubject.next(this.users);
  }

  getUsers(){
    // TODO liaison a PostPlatform par Httpclient
    return this.http.get(this.apiUrl + '/get-all');
  }

  getSingleUser(id: number) {
    // TODO liaison a PostPlatform par Httpclient
    return new Promise(
      (resolve, reject) => {
        firebase.database().ref('/users/' + id).once('value').then(
          (data) => {
            resolve(data.val());
          }, (error) => {
            reject(error);
          }
        );
      }
    );
  }
}
