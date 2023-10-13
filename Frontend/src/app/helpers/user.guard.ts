import {CanActivateFn, Router, UrlTree} from '@angular/router';
import {Observable} from "rxjs";
import {inject} from "@angular/core";
import {AuthService} from "../services/auth.service";

export const UserGuard: CanActivateFn = ():
  | Observable<boolean | UrlTree>
  | Promise<boolean | UrlTree>
  | boolean
  | UrlTree => {
  const currentUser = inject(AuthService).userValue;

  if (!currentUser) {
    return inject(Router).navigateByUrl("login");
  }

  return true;
};
