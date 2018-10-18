import {User} from './user.model';

export class Post {
  id: number;
  loveIts: number;
  date: number;
  url: string;
  poster: string;
  constructor(public title: String, public contenu: String  ) {
  }

  toJson() {
    return  '{ "contenu":' + this.contenu + ', "date":' + this.date + ', "id":' + this.id + ', "loveIts":' + this.loveIts + ', "poster":' + '"Test.recontruction@singlePost",' +
      '"title":' + this.title + ', "url":' + this.url + '}' ; }


  toString() {
    return ('    id:' + this.id + '    loveIts:' + this.loveIts + '    date:' + this.date + '    url:' + this.url + '    title:' + this.title + '    contenu:' + this.contenu + '    poster:' + this.poster);
}
}
