import {Component, OnInit} from '@angular/core';
import {Post} from '../models/post.model';
import {ActivatedRoute, Router} from '@angular/router';
import {PostService} from '../services/post.service';
import * as firebase from 'firebase';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-post-list-item-component',
  templateUrl: './post-list-item.component.html',
  styleUrls: ['./post-list-item.component.css']
})
export class PostListItemComponent implements OnInit {

  isAuth: Boolean;
  post: Post;

  constructor(private route: ActivatedRoute,
              private postService: PostService,
              private router: Router ) { }

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    this.post = this.postService.getSinglePost(+id)
    firebase.auth().onAuthStateChanged(
      (user) => {
        if (user) {
          this.isAuth = true;
        } else {
          this.isAuth = false;
        }
      }
    );
  }

  onBack() {
    this.router.navigate(['/posts']);
  }

  onLoveIt() {
    const id = this.route.snapshot.params['id'];
    this.post.loveIts = this.post.loveIts + 1;
   this.postService.modifyLoveIt(this.post, id);
  }

  onDontLoveIt() {
    const id = this.route.snapshot.params['id'];
    this.post.loveIts = this.post.loveIts - 1;
    this.postService.modifyLoveIt(this.post, id);
  }

  onDelete() {
    const id = this.route.snapshot.params['id'];
    this.postService.removePostById(id);
    this.router.navigate(['/posts']);

  }
}
