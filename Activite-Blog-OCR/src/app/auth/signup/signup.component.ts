import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {UserService} from '../../services/user.service';
import {User} from '../../models/user.model';
import {Subscription} from 'rxjs';
import {UserDto} from '../../models/userDto.model';
import {resolve} from "q";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit , OnDestroy {

  signUpForm: FormGroup;
  newUserDto: UserDto;
  userDtoSubscription: Subscription;

  usersDto: UserDto[];

  errorMassage: string;

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService,
              private router: Router) {

  }

  ngOnInit() {

    this.userDtoSubscription = this.authService.usersDtoSubject.subscribe(
      (usersDto: UserDto[]) => {
        this.usersDto = usersDto;
      }
    );

    this.initForm();

    this.authService.getUsersDto();
    this.authService.emitUsersDto();
  }

  ngOnDestroy() {
    this.userDtoSubscription.unsubscribe();
  }

  initForm() {
    this.signUpForm = this.formBuilder.group(
      {
        email: ['', [Validators.required, Validators.email]],
        pseudo: ['', [Validators.required]],
        password: ['', /*[Validators.required, Validators.pattern(/[0-9a-zA-Z]{6,}/)]*/],
        matchingPassword: ['', /*[Validators.required, Validators.pattern(/[0-9a-zA-Z]{6,}/)]*/]
      });
  }

  onSubmit() {
    const email = this.signUpForm.get('email').value;
    const pseudo = this.signUpForm.get('pseudo').value;

    // TODO ajout d'un champ de verif du password: validation Matching
    const password = this.signUpForm.get('password').value;
    const matchingPassword = this.signUpForm.get('matchingPassword').value;

    const user: UserDto = new UserDto();

    user.email = email;
    user.pseudo = pseudo;
    user.password = password;
    user.matchingPassword = matchingPassword;

    console.log(user);

    this.authService.signUpUser(user).then(
      resolve => {
        // TODO Si ok
        console.log('Registration OK');
        this.router.navigate(['/Posts']);
      },
      error => {
        // TODO Si Ereur
        this.errorMassage = error;
        console.log('Registration Fail');
      },
    );




  }
}
