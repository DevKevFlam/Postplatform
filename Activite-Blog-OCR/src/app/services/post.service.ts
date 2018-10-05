import {Injectable} from '@angular/core';
import {Post} from '../models/post.model';
import {Subject} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private apiUrl: String = 'http://localhost:9001';
  posts: Post[] = [];
  postsSubject = new Subject<Post[]>();

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };

  constructor(private http: HttpClient) {
  }

  emitPosts() {
    this.postsSubject.next(this.posts);
  }

  savePosts() {
    console.log( 'longeur du tab de post          ' + this.posts.length);
    console.log( 'Post à l\'index ' + this.posts[this.posts.length-1].toString());
    // firebase.database().ref('/posts').set(this.posts);

    const objectObservable = this.http.post<Post>(this.apiUrl + '/Posts', this.posts[this.posts.length-1], this.httpOptions).pipe();

    console.log(objectObservable);
    return objectObservable;
  }

  getPosts() {

    // Ok reconstruction de la list Users
    this.posts = [];
    this.http.get<any[]>(this.apiUrl + '/Posts').toPromise().then(
      data => {
        data.forEach(value => {
          const post = new Post(value.title, value.contenu, value.poster);
          post.id = value.id;
          post.loveIts = value.loveIts;
          post.date = value.date;
          post.url = value.url;
          this.posts.push(post)
        });
      }
    )
    //Pour debug
    console.log(this.posts);
    this.emitPosts();
  }

  getSinglePost(id: number): Post {
    const post: Post = new Post('', '', '');
    console.log(this.apiUrl + '/Posts/' + (id))
    this.http.get<Post>(this.apiUrl + '/Posts/' + (id)).toPromise().then(
      data => {
        post.id = data.id;
        post.title = data.title;
        post.contenu = data.contenu;
        post.poster = data.poster;
        post.url = data.url;
        post.date = data.date;
        post.loveIts = data.loveIts;

      }
    )
    return post;
  }

  createNewPost(newPost: Post) {
    newPost.date = Date.now();
    newPost.loveIts = 0;
    this.posts.push(newPost);
    this.savePosts().subscribe();
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
