import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';


@Injectable()
export class AuthGuardService implements CanActivate {

  private apiUrl: String = 'http://localhost:9001';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };

  constructor(private router: Router, private http: HttpClient) {
  }

  private getLoggedUsers() {
    const objectObservable = this.http.get(this.apiUrl + '/loggedUsers').toPromise();
    console.log(objectObservable);
    return objectObservable;
  }

  canActivate(): Observable<boolean> | Promise<boolean> | boolean {
    let users;
    this.getLoggedUsers().then(
      data => {
        users = data;
      },
      reject => {
        // TODO Throw Exception
      }
    );

    console.log(users);

    return new Promise(
      (resolve, reject) => {
/*
      if(users.) {}

        if (user) {
          resolve(true);
        } else {
          this.router.navigate(['/auth', 'signin']);
          resolve(false);
        }

*/
      }
    );
  }

}
