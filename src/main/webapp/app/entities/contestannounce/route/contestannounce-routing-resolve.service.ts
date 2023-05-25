import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContestannounce } from '../contestannounce.model';
import { ContestannounceService } from '../service/contestannounce.service';

@Injectable({ providedIn: 'root' })
export class ContestannounceRoutingResolveService implements Resolve<IContestannounce | null> {
  constructor(protected service: ContestannounceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContestannounce | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((contestannounce: HttpResponse<IContestannounce>) => {
          if (contestannounce.body) {
            return of(contestannounce.body);
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
