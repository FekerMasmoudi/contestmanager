import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGeneralrules } from '../generalrules.model';
import { GeneralrulesService } from '../service/generalrules.service';

@Injectable({ providedIn: 'root' })
export class GeneralrulesRoutingResolveService implements Resolve<IGeneralrules | null> {
  constructor(protected service: GeneralrulesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGeneralrules | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((generalrules: HttpResponse<IGeneralrules>) => {
          if (generalrules.body) {
            return of(generalrules.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
