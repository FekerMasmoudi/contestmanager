import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISpeciality } from '../speciality.model';
import { SpecialityService } from '../service/speciality.service';

@Injectable({ providedIn: 'root' })
export class SpecialityRoutingResolveService implements Resolve<ISpeciality | null> {
  constructor(protected service: SpecialityService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpeciality | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((speciality: HttpResponse<ISpeciality>) => {
          if (speciality.body) {
            return of(speciality.body);
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
