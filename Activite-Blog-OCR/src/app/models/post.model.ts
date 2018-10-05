export class Post {
  id: number;
  loveIts: number;
  date: number;
  url: string;
  constructor(public title: String, public contenu: String,  public poster: String) {
  }

  toString() {
    return ('    id:' + this.id + '    loveIts:' + this.loveIts + '    date:' + this.date + '    url:' + this.url + '    title:' + this.title + '    contenu:' + this.contenu + '    poster:' + this.poster);
}
}
