import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../../services/Auth/auth.service";

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  isAuth: boolean;

  constructor(private authService: AuthService) {
  }

  ngOnInit() {

    // TODO Get Auth status

  }

  onSignOut() {
    // TODO Log Out
    // this.authService.signOutUser();
  }

}
