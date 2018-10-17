import {Injectable} from '@angular/core';
import {Post} from '../models/post.model';
import {Subject} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private apiUrl: String = 'http://localhost:9001';

  posts: Post [] = [];
  postsSubject = new Subject<Post[]>();

  postEnCour: Post = null;
  postSubject = new Subject<Post>();

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

  emitPostEnCour() {
    this.postSubject.next(this.postEnCour);
  }


  //////////////////////////////////// HTTP Request for PostPlatform: POST, PATCH, DELETE
  //OK
  private savePosts() {
    const objectObservable = this.http.post(this.apiUrl + '/Posts', this.posts[this.posts.length - 1], this.httpOptions).pipe();
    return objectObservable;
  }

  //OK
  private updatePosts(id: number) {
    const objectObservable = this.http.patch(this.apiUrl + '/Posts', this.posts[id], this.httpOptions).pipe();
    return objectObservable;
  }

  //Ok
  private deletePost(id: number) {
    const objectObservable = this.http.delete(this.apiUrl + '/Posts/' + id, this.httpOptions).pipe();
    return objectObservable;
  }

  /////////////////////////////////// HTTP Request for PostPlatform: GET
  // OK
  getPosts() {

    // Ok reconstruction de la list Users
    this.posts = [];
    this.http.get<Post[]>(this.apiUrl + '/Posts').toPromise().then(
      data => {
        data.forEach(value => {

          let post: Post;
          post = new Post(value.title, value.contenu, value.poster);
          post.id = value.id;
          post.loveIts = value.loveIts;
          post.date = value.date;
          post.url = value.url;
          //Pour debug
          //console.log(post);

          this.posts.push(post);

        });

      }
    )
    // Pour debug
    // console.log(this.posts);
    this.emitPosts();
  }

  // OK
  getSinglePost(id: number): Post {
    this.postEnCour = new Post('', '', '');
    this.http.get<Post>(this.apiUrl + '/Posts/' + (id)).toPromise().then(
      data => {

        this.postEnCour.id = data.id;
        this.postEnCour.title = data.title;
        this.postEnCour.contenu = data.contenu;
        this.postEnCour.poster = data.poster;
        this.postEnCour.url = data.url;
        this.postEnCour.date = data.date;
        this.postEnCour.loveIts = data.loveIts;

          if (this.postEnCour.title === '' || this.postEnCour.title === null) {
            // TODO Post introuvable exception
            console.log('Post introuvable exception!!!');
          }

      }
    )
    this.emitPostEnCour();
    return this.postEnCour;
  }

  ////////////////////////////////////
  // OK
  createNewPost(newPost: Post) {
    // TODO Renseigner newPost.poster
    newPost.date = Date.now();
    newPost.loveIts = 0;
    this.posts.push(newPost);
    this.savePosts().subscribe();
    this.emitPosts();
  }

  // OK
  updatePost(post: Post, id: number) {
    this.posts[post.id] = post;
    post.date = Date.now();
    this.updatePosts(id).subscribe();
    this.emitPosts();
  }

  // OK
  removePost(post: Post) {
    const bookIndexToRemove = this.posts.findIndex(
      (postEl) => {
        if (postEl === post) {
          return true;
        }
      }
    );
    this.posts.splice(bookIndexToRemove, 1);
    this.deletePost(bookIndexToRemove).subscribe();
    this.emitPosts();
  }

  // OK
  removePostById(id: number) {
    this.posts.splice(id, 1);
    this.deletePost(id).subscribe();
    this.emitPosts();
  }

}
