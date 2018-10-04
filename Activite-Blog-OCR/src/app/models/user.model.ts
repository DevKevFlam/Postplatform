export class User {
  id: number;
  mdp: string;

  constructor(public email: string, public pseudo: string) {
  }

  toJson(){
    return  '{"id":' + this.id +
            ',"email":"' + this.email +
            '","pseudo":"' + this.pseudo +
            '","mdp":"' + this.mdp +'"}' }
}
