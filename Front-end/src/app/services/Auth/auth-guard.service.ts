import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {AppComponent} from '../../app.component';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {

  constructor(
    private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {


    // Récupération du CurrentUser dans SessionStorage
    const session = sessionStorage.getItem(AppComponent.SESSION);

    // DEBUG
    // console.log('//// canActivate ////' + session + '//// canActivate ////');

    if (session) {
      // logged in so return true
      return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate(['/login'], {queryParams: {returnUrl: state.url}});
    return false;
  }
}
