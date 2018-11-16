import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {User} from "../../../model/model.user";
import {Router} from "@angular/router";
import {RegistationService} from "../../../services/Auth/registation.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit {
  user: User = new User();
  errorMessage: string;

  constructor(public registationService: RegistationService, public router: Router) {
  }

  ngOnInit() {
  }

  // OK
  register() {
    this.registationService.createAccount(this.user).subscribe(data => {
      // Redirection vers la page login
        this.router.navigate(['/login']);
      }, err => {
        console.log(err);
        this.errorMessage = 'username already exist';
        // TODO Erreur Registration
      }
    );
  }
}
