import {Injectable} from '@angular/core';
import {Post} from '../models/post.model';
import {Subject} from 'rxjs';
import * as firebase from 'firebase';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  posts: Post[] = [];
  postsSubject = new Subject<Post[]>();

  constructor() {
  }

  emitPosts() {
    this.postsSubject.next(this.posts);
  }

  savePosts() {
    firebase.database().ref('/posts').set(this.posts);
  }

  getPosts() {
    firebase.database().ref('/posts').on('value', (data) => {
      this.posts = data.val() ? data.val() : [];
      this.emitPosts();
    });
  }

  getSinglePost(id: number) {
    return new Promise(
      (resolve, reject) => {
        firebase.database().ref('/posts/' + id).once('value').then(
          (data) => {
            resolve(data.val());
          }, (error) => {
            reject(error);
          }
        );
      }
    );
  }

  createNewPost(newPost: Post) {
    newPost.date = Date.now();
    newPost.loveIts = 0;
    this.posts.push(newPost);
    this.savePosts();
    this.emitPosts();
  }

  updatePost(post: Post, id: number) {
    this.getPosts();
    post.date = Date.now();
    this.posts[id] = post;
    this.savePosts();
    this.emitPosts();
  }

  removePost(post: Post) {
    const bookIndexToRemove = this.posts.findIndex(
      (postEl) => {
        if (postEl === post) {
          return true;
        }
      }
    );
    this.posts.splice(bookIndexToRemove, 1);
    this.savePosts();
    this.emitPosts();
  }

  removePostById(id: number) {
    this.posts.splice(id, 1);
    this.savePosts();
    this.emitPosts();
  }

  modifyLoveIt(post: Post, id: number) {
    this.getPosts();
    this.posts[id].loveIts = post.loveIts;
    this.savePosts();
    this.emitPosts();
  }
}
