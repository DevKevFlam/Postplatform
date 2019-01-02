import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit, OnDestroy {

  isAuth: boolean;
  posts: Post[];
  postsSubscription: Subscription;

  constructor(private postsService: PostService, private router: Router) { }

  ngOnInit() {
    this.posts = [];
    this.postsSubscription = this.postsService.postsSubject.subscribe(
      (posts: Post[]) => {
        console.log(posts);
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
    this.router.navigate(['/posts', 'update', id + 1 ]);
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
