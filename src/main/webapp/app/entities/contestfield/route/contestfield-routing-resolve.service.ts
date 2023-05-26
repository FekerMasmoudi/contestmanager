import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContestfield } from '../contestfield.model';
import { ContestfieldService } from '../service/contestfield.service';

@Injectable({ providedIn: 'root' })
export class ContestfieldRoutingResolveService implements Resolve<IContestfield | null> {
  constructor(protected service: ContestfieldService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContestfield | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((contestfield: HttpResponse<IContestfield>) => {
          if (contestfield.body) {
            return of(contestfield.body);
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
