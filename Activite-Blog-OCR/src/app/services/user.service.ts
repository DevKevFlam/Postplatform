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
  usersSubject = new Subject<User[]>();

  userEnCour: User = null;
  userSubject = new Subject<User>();

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };

  constructor(private http: HttpClient) {
  }

  emitUsers() {
    this.usersSubject.next(this.users);
  }

  emitUserEnCour() {
    //this.userSubject.next(this.userEnCour);
  }

  //////////////////////////////////// HTTP Request for PostPlatform: POST, PATCH, DELETE

  private saveUsers() {
    const objectObservable = this.http.post<User[]>(this.apiUrl + '/Users', this.users, this.httpOptions).pipe();
    return objectObservable;
  }

  //OK
  private updateUsers(id: number) {
    const objectObservable = this.http.patch(this.apiUrl + '/Users', this.users[id], this.httpOptions).pipe();
    return objectObservable;
  }


  /////////////////////////////////// HTTP Request for PostPlatform: GET
  getUsers() {
    // Ok reconstruction de la list Users
    this.users = [];
    this.http.get<User[]>(this.apiUrl + '/Users').toPromise().then(
      data => {
        data.forEach(value => {

          let user: User;
          user = new User(value.email, value.pseudo);
          user.id = value.id;
          user.mdp = "";//value.mdp;
          //Pour debug
          //console.log(user);

          this.users.push(user);

        });
      }
    );

    // Pour debug
    // console.log(this.users);
    this.emitUsers();
  }

  getSingleUser(id: number): User {
    this.userEnCour = new User('', '');

    this.http.get<User>(this.apiUrl + '/Users/' + (id)).toPromise().then(
      data => {
        this.userEnCour.id = data.id;
        this.userEnCour.pseudo = data.pseudo;
        this.userEnCour.email = data.email;
        this.userEnCour.mdp = data.mdp;

        if (this.userEnCour.email === '' || this.userEnCour.email === null) {
          // TODO User introuvable exception
          console.log("User introuvable exception!!!")
        }
      }
    );
    this.emitUserEnCour();
    return this.userEnCour;
  }

  ////////////////////////////////////

  createNewUser(newUser: User) {
    // TODO Traiter le mdp
    newUser.id = 0;
    this.users.push(newUser);
    this.saveUsers().subscribe();
    this.emitUsers();

  }

  updateUser(user: User, id: number) {
    this.users[id] = user;
    this.updateUsers(id);
    this.emitUsers();
  }


  //////////////////////////////////// HTTP Request for PostPlatform: ADMIN DELETE USERS

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
