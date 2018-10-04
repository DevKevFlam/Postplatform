import { Component, OnInit } from '@angular/core';
import {Post} from "../models/post.model";
import {User} from "../models/user.model";
import {ActivatedRoute, Router} from "@angular/router";
import {PostService} from "../services/post.service";
import {UserService} from "../services/user.service";
import * as firebase from "firebase";

@Component({
  selector: 'app-user-item',
  templateUrl: './user-item.component.html',
  styleUrls: ['./user-item.component.css']
})
export class UserItemComponent implements OnInit {

  isAuth: Boolean;
  user: User;

  constructor(private route: ActivatedRoute,
              private userService: UserService,
              private router: Router) { }

  ngOnInit() {
    this.user = new User('','');
    const id = this.route.snapshot.params['id'];
    console.log(id);
    this.user = this.userService.getSingleUser(id);
    /*
    firebase.auth().onAuthStateChanged(
      (user) => {
        if (user) {
          this.isAuth = true;
        } else {
          this.isAuth = false;
        }
      }
    );*/
  }
  onBack() {
    this.router.navigate(['/users']);
  }
}
