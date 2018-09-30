import {Component, OnDestroy, OnInit} from '@angular/core';
import {Post} from '../models/post.model';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';
import {PostService} from '../services/post.service';
import * as firebase from 'firebase';


@Component({
  selector: 'app-post-list-component',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit, OnDestroy {

  isAuth: boolean;
  posts: Post[];
  postsSubscription: Subscription;

  constructor(private postsService: PostService, private router: Router) { }

  ngOnInit() {
    this.postsSubscription = this.postsService.postsSubject.subscribe(
      (posts: Post[]) => {
        this.posts = posts;
      }
    );
    firebase.auth().onAuthStateChanged(
      (user) => {
        if (user) {
          this.isAuth = true;
        } else {
          this.isAuth = false;
        }
      }
    );
    this.postsService.getPosts();
    this.postsService.emitPosts();
  }

  onNewPost() {
    this.router.navigate(['/posts', 'new']);
  }

  onModifyPost(id: number) {
    this.router.navigate(['/posts', 'update', id]);
  }

  onDeletePost(post: Post) {
    this.postsService.removePost(post);
  }

  onViewPost(id: number) {
    this.router.navigate(['/posts', 'view', id]);
  }

  ngOnDestroy() {
    this.postsSubscription.unsubscribe();
  }
}
