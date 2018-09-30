import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Post} from '../models/post.model';
import {PostService} from '../services/post.service';
import {Router} from '@angular/router';
import * as firebase from 'firebase';

@Component({
  selector: 'app-new-post',
  templateUrl: './new-post.component.html',
  styleUrls: ['./new-post.component.css']
})
export class NewPostComponent implements OnInit {

  postForm: FormGroup;
  newPost: Post;

  constructor(private formBuilder: FormBuilder,
              private postService: PostService,
              private router: Router) { }

  ngOnInit() {
    this.initForm();
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
    this.router.navigate(['/posts']);
  }
}
