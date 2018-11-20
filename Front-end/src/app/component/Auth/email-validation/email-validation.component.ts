import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RegistationService} from '../../../services/Auth/registation.service';

@Component({
  selector: 'app-email-validation',
  templateUrl: './email-validation.component.html',
  styleUrls: ['./email-validation.component.css']
})
export class EmailValidationComponent implements OnInit {

  constructor(public Aroute: ActivatedRoute,
              private regisServ: RegistationService,
              private route: Router) {
  }

  ngOnInit() {
    const token = this.Aroute.snapshot.params['token'];
    console.log('token: '+ token);
    this.regisServ.verifyMail(token).subscribe(data => {
        // Redirection vers la page login
      setTimeout( () => { this.route.navigate(['/']);},3000);
      }, err => {
    // TODO Erreur de verif
    }
    );

  }

}
