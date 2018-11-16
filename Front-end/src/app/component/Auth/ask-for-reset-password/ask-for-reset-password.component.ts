import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RegistationService} from '../../../services/Auth/registation.service';
import {User} from '../../../model/model.user';

@Component({
  selector: 'app-ask-for-reset-password',
  templateUrl: './ask-for-reset-password.component.html',
  styleUrls: ['./ask-for-reset-password.component.css']
})
export class AskForResetPasswordComponent implements OnInit {

  user: User = new User();
  errorMessage: string;

  constructor(public Aroute: ActivatedRoute, public registationService: RegistationService, public router: Router) { }

  ngOnInit() {
  }

  onSubmit(){
    this.registationService.askForResetPassword(this.user.username).subscribe(
      (data:any) => {
        // TODO affichage "regarder vos mail"
      },
      err => {
        console.log(err);
        this.errorMessage = 'username not found';
        // TODO Erreur Reset password
      }
    )

  }

}
