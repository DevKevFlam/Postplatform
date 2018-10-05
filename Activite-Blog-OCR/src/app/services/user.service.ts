import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {User} from '../models/user.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl: String = 'http://localhost:9001';
  users: User[] = [];

  userSubject = new Subject<User[]>();

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };


  constructor(private http: HttpClient) {
  }

  emitUsers() {
    this.userSubject.next(this.users);
  }

  saveUsers() {
    const objectObservable = this.http.post<User>(this.apiUrl + '/add-one', this.users, this.httpOptions).pipe();

    console.log(objectObservable);
    return objectObservable;
  }

  getUsers() {
    // Ok reconstruction de la list Users
    this.users = [];
    this.http.get<any[]>(this.apiUrl + '/get-all').toPromise().then(
      data => {
        data.forEach(value => {
          const user = new User(value.email, value.pseudo);
          user.id = value.id;
          user.mdp = value.mdp;
          this.users.push(user);
        });
      }
    );
    // Pour debug
    // console.log(this.users);
    this.emitUsers();
  }

  getSingleUser(id: number): User {
    const user: User = new User('', '');
    console.log(this.apiUrl + '/get-one/' + (id));
    this.http.get<User>(this.apiUrl + '/get-one/' + (id)).toPromise().then(
      data => {
        user.id = data.id;
        user.pseudo = data.pseudo;
        user.email = data.email;
        user.mdp = data.mdp;
      }
    );
    return user;
  }

  createNewUser(newUser: User) {
    newUser.id = 0;
    this.users.push(newUser);
    this.saveUsers();
    this.emitUsers();

  }

  updateUser(user: User, id: number) {
    this.getUsers();
    ;
    this.users[id] = user;
    this.saveUsers();
    this.emitUsers();
  }

  removeUser(user: User) {
    const userIndexToRemove = this.users.findIndex(
      (userEl) => {
        if (userEl === user) {
          return true;
        }
      }
    );
    this.users.splice(userIndexToRemove, 1);
    this.saveUsers();
    this.emitUsers();
  }

  removePostById(id: number) {
    this.users.splice(id, 1);
    this.saveUsers();
    this.emitUsers();
  }
}
