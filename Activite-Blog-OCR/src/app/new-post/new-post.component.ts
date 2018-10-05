import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Post} from '../models/post.model';
import {PostService} from '../services/post.service';
import {Router} from '@angular/router';
import * as firebase from 'firebase';
import {User} from "../models/user.model";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-new-post',
  templateUrl: './new-post.component.html',
  styleUrls: ['./new-post.component.css']
})
export class NewPostComponent implements OnInit , OnDestroy {

  postForm: FormGroup;
  newPost: Post;
  postSubscription: Subscription;

  posts: Post[];

  constructor(private formBuilder: FormBuilder,
              private postService: PostService,
              private router: Router) { }

  ngOnInit() {
    this.postSubscription = this.postService.postsSubject.subscribe(
      (posts: Post[]) => {
        this.posts = posts;
      }
    );

    this.initForm();

    console.log(this.posts)
    this.postService.getPosts();
    this.postService.emitPosts();
  }

  ngOnDestroy() {
    this.postSubscription.unsubscribe();
  }

  initForm() {

    this.postForm = this.formBuilder.group({
      title: ['', Validators.required],
      contenu: ['', Validators.required],
      url: [null, Validators]
    });
  }

  onSavePost() {
    const title = this.postForm.get('title').value;
    const contenu = this.postForm.get('contenu').value;
    if (this.postForm.get('url')) {
      this.newPost = new Post(title , contenu, firebase.auth().currentUser.email );
      this.newPost.url = this.postForm.get('url').value;
    } else {
      this.newPost = new Post(title , contenu, firebase.auth().currentUser.email );
    }
    this.postService.createNewPost(this.newPost);
    //this.router.navigate(['/posts']);
  }
}
