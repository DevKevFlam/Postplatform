import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {UserService} from '../../services/user.service';
import {User} from '../../models/user.model';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  signInForm: FormGroup;
  errorMassage: string;

  users: User[];
  userSubscription: Subscription;

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService,
              private userService: UserService,
              private router: Router) {
  }

  ngOnInit() {
    this.initForm();
    this.userSubscription = this.userService.usersSubject.subscribe(
      (users: User[]) => {
        this.users = users;
        this.userService.getUsers();
        this.userService.emitUsers();
      }
    );
  }

  initForm() {
    this.signInForm = this.formBuilder.group(
      {
        email: ['', [Validators.required, Validators.email]],
        // pseudo: ['', [Validators.required]],
        password: ['', [Validators.required, Validators.pattern(/[0-9a-zA-Z]{6,}/)]]
      }
    );
  }

  onSubmit() {
    const email = this.signInForm.get('email').value;
    const password = this.signInForm.get('password').value;

    this.authService.signInUser(email, password).then(
      () => {
        console.log('retour auth to comp ok !!!');
        // this.router.navigate(['/books']);
      },
      (error) => {
        this.errorMassage = error;
        console.log('retour auth to comp fail  // ' + error);
      }
    );
  }
}
