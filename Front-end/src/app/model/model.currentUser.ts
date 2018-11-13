import {User} from "./model.user";

export class ModelCurrentUser {
  authorities: string[];
  details: string ;
  authenticated: boolean ;
  principal: User ;
  credentials: string;
  name: string;

}
