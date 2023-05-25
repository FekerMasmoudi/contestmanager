import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEducationlevel } from '../educationlevel.model';
import { EducationlevelService } from '../service/educationlevel.service';

@Injectable({ providedIn: 'root' })
export class EducationlevelRoutingResolveService implements Resolve<IEducationlevel | null> {
  constructor(protected service: EducationlevelService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEducationlevel | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((educationlevel: HttpResponse<IEducationlevel>) => {
          if (educationlevel.body) {
            return of(educationlevel.body);
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
