import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  static SESSION = 'currentUser';
  static SESSION_TIME_EXPIRE = 1;
  static SESSION_TIME_EXPIRE_UNIT = 'd';
  static API_URL = 'http://localhost:8080';
  static title = 'Front-end pour PostPlatform';

}
