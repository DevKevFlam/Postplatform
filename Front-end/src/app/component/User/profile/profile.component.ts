import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AuthService} from '../../../services/Auth/auth.service';
import {Router} from '@angular/router';
import {LocalStorageService, SessionStorage, SessionStorageService} from "angular-web-storage";
import {User} from "../../../model/model.user";
import {AppComponent} from "../../../app.component";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  encapsulation: ViewEncapsulation.None

})
export class ProfileComponent implements OnInit {

  currentUser: User;

  constructor(public local: LocalStorageService,
              public session: SessionStorageService,
              public authService: AuthService,
              public router: Router) { }

  ngOnInit() {
    this.currentUser =  <User> this.session.get(AppComponent.SESSION);

  }

  // login out from the app
  onlogOut() {
    this.authService.logOut()
     .subscribe(
        data => {
          // Redirection vers la page login
          this.router.navigate(['/login']);
        },
        error => {

        });
  }

}
