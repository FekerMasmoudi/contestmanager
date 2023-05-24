import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFields } from '../fields.model';
import { FieldsService } from '../service/fields.service';

@Injectable({ providedIn: 'root' })
export class FieldsRoutingResolveService implements Resolve<IFields | null> {
  constructor(protected service: FieldsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFields | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fields: HttpResponse<IFields>) => {
          if (fields.body) {
            return of(fields.body);
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
