import {Component, OnInit} from '@angular/core';

import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isAuth: boolean;

  constructor(private authService: AuthService) {
  }

  ngOnInit() {
    // TODO recup session
    // firebase.auth().onAuthStateChanged(
    //   (user) => {
    //     if (user) {
    //       this.isAuth = true;
    //     } else {
    //       this.isAuth = false;
    //     }
    //   }
    // );
    this.isAuth = true;
  }

  onSignOut() {
    //
    this.authService.signOutUser();
  }

}
