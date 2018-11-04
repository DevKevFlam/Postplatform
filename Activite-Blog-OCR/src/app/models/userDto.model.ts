
export class UserDto {

  password: string;
  matchingPassword: string;
  email: string;
  pseudo: string;

  constructor() {}

  toString() {
   return '\'password\':\'' + this.password + '\',\'matchingPassword\':\'' + this.matchingPassword + '\',\'email\':\'' + this.email + '\',\'pseudo\':\'' + this.pseudo + '\'';
  }
}
