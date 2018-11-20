import { Component, OnInit } from '@angular/core';
import {RegistationService} from '../../../services/Auth/registation.service';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../../../model/model.user';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  user: User = new User();
  errorMessage: string;
  token: string;

  constructor(public Aroute: ActivatedRoute, public registationService: RegistationService, public route: Router) { }

  ngOnInit() {
    this.token = this.Aroute.snapshot.params['token'];
  }

  onSubmit(){

    this.registationService.resetPassword(this.token, this.user ).subscribe(data => {
      // Redirection vers la page login
      setTimeout( () => { this.route.navigate(['/']);},3000);

      }, err => {
        console.log(err);
        this.errorMessage = '';
        // TODO Erreur Reset password
      }
    );

  }
}
