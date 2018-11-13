import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../../../model/model.user';
import {AuthService} from '../../../services/Auth/auth.service';
import {SessionStorageService} from "angular-web-storage";
import {AppComponent} from "../../../app.component";
import {ExpiredUnit} from "angular-web-storage/src/util";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {

  user: User = new User();
  errorMessage: string;

  constructor(public session: SessionStorageService,
              private authService: AuthService,
              private router: Router) {
  }

  ngOnInit() {
    // DEBUG StorageSession avant login (après logout) Should be NULL
    // console.log('//// AVANT LOGIN /////' + sessionStorage.getItem(AppComponent.SESSION));
  }

  login() {
    this.authService.logIn(this.user)
      .subscribe((data: any) => {
          const user: User = data.principal;
          if (user) {
            // Création de CurrentUser Dans le SessionStorage (cache local)
            this.session.set(AppComponent.SESSION, user,
              AppComponent.SESSION_TIME_EXPIRE, <ExpiredUnit> AppComponent.SESSION_TIME_EXPIRE_UNIT);
          }
          this.router.navigate(['/profile']);
        }, err => {
          this.errorMessage = 'error :  Username or password is incorrect';
        }
      );
  }

}
