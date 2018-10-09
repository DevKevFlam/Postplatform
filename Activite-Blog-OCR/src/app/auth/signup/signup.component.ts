import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {UserService} from '../../services/user.service';
import {User} from '../../models/user.model';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit , OnDestroy {
  signUpForm: FormGroup;
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
      }
    );
    /*
    //TODO IsAuth initialisation
    firebase.auth().onAuthStateChanged(
      (user) => {
        if (user) {
          this.isAuth = true;
        } else {
          this.isAuth = false;
        }
      }
    );*/
    this.userService.getUsers();
    this.userService.emitUsers();
  }


  ngOnDestroy() {
    this.userSubscription.unsubscribe();
  }

  initForm() {
    this.signUpForm = this.formBuilder.group(
      {
        email: ['', [Validators.required, Validators.email]],
        pseudo: ['', [Validators.required]],
        password: ['', [Validators.required, Validators.pattern(/[0-9a-zA-Z]{6,}/)]]
      });
  }

  onSubmit() {
    const email = this.signUpForm.get('email').value;
    const pseudo = this.signUpForm.get('pseudo').value;
    const password = this.signUpForm.get('password').value;

    const user: User = new User(email, pseudo);
    user.mdp = password;
    this.userService.createNewUser(user);
    this.authService.createNewUser(user).then(
      () => {
        // TODO gestion de l'authentification et MDP
      },
      (error) => {
        this.errorMassage = error;
      }
    );

  }
}
