import {Component, OnInit} from '@angular/core';
import {Post} from '../models/post.model';
import {ActivatedRoute, Router} from '@angular/router';
import {PostService} from '../services/post.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-post-list-item-component',
  templateUrl: './post-list-item.component.html',
  styleUrls: ['./post-list-item.component.css']
})
export class PostListItemComponent implements OnInit {

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
    this.post = this.postService.getSinglePostById(+id);

    // TODO Init isAuth sans firebase
    // firebase.auth().onAuthStateChanged(
    //   (user) => {
    //     if (user) {
    //       this.isAuth = true;
    //     } else {
    //       this.isAuth = false;
    //     }
    //   }
    // );
    this.isAuth = true;
  }

  onBack() {
    this.router.navigate(['/posts']);
  }

  onLoveIt() {
    const id = this.route.snapshot.params['id'];
    this.post.loveIts = this.post.loveIts + 1;
   this.postService.updateLoveItsPost(this.post, id);
  }

  onDontLoveIt() {
    const id = this.route.snapshot.params['id'];
    this.post.loveIts = this.post.loveIts - 1;
    this.postService.updateLoveItsPost(this.post, id);
  }

  onDelete() {
    const id = this.route.snapshot.params['id'];
    this.postService.removePostById(id);
    this.router.navigate(['/posts']);

  }
}
