import { Component, OnInit } from '@angular/core';
import {Post} from '../models/post.model';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {PostService} from '../services/post.service';
import * as firebase from 'firebase';
import {Subscription} from "rxjs";

@Component({
  selector: 'app-update-post',
  templateUrl: './update-post.component.html',
  styleUrls: ['./update-post.component.css']
})
export class UpdatePostComponent implements OnInit {

  isAuth: Boolean;
  post: Post;
  postForm: FormGroup;
  postSubscription: Subscription;

  constructor(private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private postService: PostService,
              private router: Router) {
  }

  ngOnInit() {
    // TODO Init isAuth sans firebase
    const id = this.route.snapshot.params['id'];
    this.postSubscription = this.postService.postSubject.subscribe(
      (post: Post) => {
        this.post = post;
      }
    );
    this.post = this.postService.getSinglePost(+id);
    this.initForm();
  }

  initForm() {
    this.postForm = this.formBuilder.group({
      title: ['', Validators.required],
      contenu: ['', Validators.required],
      url: [null, Validators]
    });
  }

  onUpdatePost() {
    const id = this.route.snapshot.params['id'];
    if (this.postForm.get('title').value !== this.post.title
      && this.postForm.get('title').value !== ''
      && this.postForm.get('title').value !== null) {
      this.post.title = this.postForm.get('title').value;
      this.post.poster = firebase.auth().currentUser.email;
    }

    if (this.postForm.get('contenu').value !== this.post.contenu
      && this.postForm.get('contenu').value !== ''
      && this.postForm.get('contenu').value !== null) {
      this.post.contenu = this.postForm.get('contenu').value;
      this.post.poster = firebase.auth().currentUser.email;
    }

    if (this.postForm.get('url').value !== this.post.contenu
      && this.postForm.get('url').value !== ''
      && this.postForm.get('url').value !== null) {
      this.post.url = this.postForm.get('url').value;
      this.post.poster = firebase.auth().currentUser.email;
    }

    this.postService.updatePost(this.post, id);
    this.router.navigate(['/posts']);
  }
}
