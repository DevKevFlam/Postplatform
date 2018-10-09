import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {User} from '../models/user.model';
import {UserService} from '../services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit, OnDestroy {

  isAuth: boolean;
  users: User[] = [];
  userSubscription: Subscription;

  constructor(private userService: UserService, private router: Router) {
    this.users = [];
  }

  ngOnInit() {

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

  onNewUser() {
    this.router.navigate(['/users', 'new']);
  }

  onModifyUser(id: number) {
    this.router.navigate(['/users', 'update', id]);
  }

  onDeleteUser(user: User) {
   // this.userService.removeUser(user);
  }

  onViewUser(id: number) {
    this.router.navigate(['/users', 'view', id]);
  }

  ngOnDestroy() {
    this.userSubscription.unsubscribe();
  }
}
