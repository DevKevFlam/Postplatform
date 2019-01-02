import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Subscription} from "rxjs";
import {Post} from "../../../model/model.post";
import {PostService} from "../../../services/posts.service";

@Component({
  selector: 'app-post-item',
  templateUrl: './post-item.component.html',
  styleUrls: ['./post-item.component.css']
})
export class PostItemComponent implements OnInit {

  isAuth: Boolean;
  post: Post;
  postSubscription: Subscription;

  constructor(private route: ActivatedRoute,
              private postService: PostService,
              private router: Router ) { }

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    this.postSubscription = this.postService.postSubject.subscribe(
      (post: Post) => {
        this.post = post;
      }
    );
    this.post = this.postService.getSinglePost(+id);

    // TODO Init isAuth

  }

  onBack() {
    this.router.navigate(['/posts']);
  }

  onLoveIt() {
    const id = this.route.snapshot.params['id'];
    this.post.loveIts = this.post.loveIts + 1;
    this.postService.updatePost(this.post, id);
  }

  onDontLoveIt() {
    const id = this.route.snapshot.params['id'];
    this.post.loveIts = this.post.loveIts - 1;
    this.postService.updatePost(this.post, id);
  }

  onDelete() {
    const id = this.route.snapshot.params['id'];
    this.postService.removePostById(id);
    this.router.navigate(['/posts']);

  }

}
