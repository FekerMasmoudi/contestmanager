import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEducationlevel, NewEducationlevel } from '../educationlevel.model';

export type PartialUpdateEducationlevel = Partial<IEducationlevel> & Pick<IEducationlevel, 'id'>;

export type EntityResponseType = HttpResponse<IEducationlevel>;
export type EntityArrayResponseType = HttpResponse<IEducationlevel[]>;

@Injectable({ providedIn: 'root' })
export class EducationlevelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/educationlevels');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(educationlevel: NewEducationlevel): Observable<EntityResponseType> {
    return this.http.post<IEducationlevel>(this.resourceUrl, educationlevel, { observe: 'response' });
  }

  update(educationlevel: IEducationlevel): Observable<EntityResponseType> {
    return this.http.put<IEducationlevel>(`${this.resourceUrl}/${this.getEducationlevelIdentifier(educationlevel)}`, educationlevel, {
      observe: 'response',
    });
  }

  partialUpdate(educationlevel: PartialUpdateEducationlevel): Observable<EntityResponseType> {
    return this.http.patch<IEducationlevel>(`${this.resourceUrl}/${this.getEducationlevelIdentifier(educationlevel)}`, educationlevel, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IEducationlevel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEducationlevel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEducationlevelIdentifier(educationlevel: Pick<IEducationlevel, 'id'>): string {
    return educationlevel.id;
  }

  compareEducationlevel(o1: Pick<IEducationlevel, 'id'> | null, o2: Pick<IEducationlevel, 'id'> | null): boolean {
    return o1 && o2 ? this.getEducationlevelIdentifier(o1) === this.getEducationlevelIdentifier(o2) : o1 === o2;
  }

  addEducationlevelToCollectionIfMissing<Type extends Pick<IEducationlevel, 'id'>>(
    educationlevelCollection: Type[],
    ...educationlevelsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const educationlevels: Type[] = educationlevelsToCheck.filter(isPresent);
    if (educationlevels.length > 0) {
      const educationlevelCollectionIdentifiers = educationlevelCollection.map(
        educationlevelItem => this.getEducationlevelIdentifier(educationlevelItem)!
      );
      const educationlevelsToAdd = educationlevels.filter(educationlevelItem => {
        const educationlevelIdentifier = this.getEducationlevelIdentifier(educationlevelItem);
        if (educationlevelCollectionIdentifiers.includes(educationlevelIdentifier)) {
          return false;
        }
        educationlevelCollectionIdentifiers.push(educationlevelIdentifier);
        return true;
      });
      return [...educationlevelsToAdd, ...educationlevelCollection];
    }
    return educationlevelCollection;
  }
}
