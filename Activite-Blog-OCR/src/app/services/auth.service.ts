import {Injectable} from '@angular/core';
import {UserService} from '../services/user.service';
import {UserDto} from '../models/userDto.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Subject} from 'rxjs';
import {Post} from "../models/post.model";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl: String = 'http://localhost:9001';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };

  users: UserDto [] = [];
  usersDtoSubject = new Subject<UserDto[]>();

  userEnCour: UserDto  = null;
  userDtoUsingSubject = new Subject<UserDto>();


  constructor(private serviceUser: UserService, private http: HttpClient) {}

  emitUsersDto() {
    this.usersDtoSubject.next(this.users);
  }

  emitUsersDtoEnCour() {
    this.userDtoUsingSubject.next(this.userEnCour);
  }

  // TODO liaison avec Spring Security et Service Auth from PostPlatform BAckend

  /////////////////////////////////// HTTP Request for Registration : GET
  getUsersDto() {/*
    this.users = [];
    this.http.get<UserDto[]>(this.apiUrl + '/user').toPromise().then(
      data => {
        data.forEach(value => {

          let userDto: UserDto;
          userDto = new UserDto();
          userDto.pseudo = value.pseudo;
          userDto.email = value.email;
          userDto.password = value.password;
          userDto.matchingPassword = value.matchingPassword;

          // Pour debug
          // console.log(post);
          this.users.push(userDto);
        });

      }
    )
    // Pour debug
    // console.log(this.posts);*/
    this.emitUsersDto();
  }

  // TODO activeUserManagement
  /*
  getSingleUserDto(user: UserDto): UserDto {
    this.userEnCour = user;
    this.http.get<UserDto>(this.apiUrl + '/Posts/' + user).toPromise().then(
      data => {

        this.userEnCour.pseudo = data.pseudo;
        this.userEnCour.email = data.email;
        this.userEnCour.password = data.password;
        this.userEnCour.matchingPassword = data.matchingPassword;
      }
    )
    this.emitUsersDtoEnCour();
    return this.userEnCour;
  }
*/

  //////////////////////////////////// HTTP Request for Registration

  private registerUserAccount(user: UserDto) {
    const objectObservable = this.http.post(this.apiUrl + '/user/registration', user, this.httpOptions).toPromise();
    console.log(objectObservable);
    return objectObservable;
  }

  private saveUsersDto() {
    const objectObservable = this.http.post(this.apiUrl + '/user/registration', this.users[this.users.length - 1], this.httpOptions).toPromise();
    return objectObservable;
  }

  ////////////////////////////////////

  signUpUser(user: UserDto) {

    // this.users.push(user);
    // const promiseOk = this.saveUsersDto();

    const promiseOk = this.registerUserAccount(user);

    // TODO gestion de l'authentification et MDP

    // TODO: Validation ou erreurs + routage vers "/Posts"

    this.emitUsersDto();
    return promiseOk;
  }


  // TODO SignIn SignOut
  signInUser(email: string, password: string, matchingPassword: string, pseudo: string) {
    const userDto = new UserDto();

    userDto.email = email;
    userDto.pseudo = pseudo;
    userDto.password = password;
    userDto.matchingPassword = matchingPassword;

    return new Promise(
      (resolve, reject) => {
        this.http.post(this.apiUrl + '/user/registration', userDto, this.httpOptions).toPromise().then(
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

  }
}
