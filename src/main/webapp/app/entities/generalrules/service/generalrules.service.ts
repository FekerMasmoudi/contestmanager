import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGeneralrules, NewGeneralrules } from '../generalrules.model';

export type PartialUpdateGeneralrules = Partial<IGeneralrules> & Pick<IGeneralrules, 'id'>;

export type EntityResponseType = HttpResponse<IGeneralrules>;
export type EntityArrayResponseType = HttpResponse<IGeneralrules[]>;

@Injectable({ providedIn: 'root' })
export class GeneralrulesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/generalrules');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(generalrules: NewGeneralrules): Observable<EntityResponseType> {
    return this.http.post<IGeneralrules>(this.resourceUrl, generalrules, { observe: 'response' });
  }

  update(generalrules: IGeneralrules): Observable<EntityResponseType> {
    return this.http.put<IGeneralrules>(`${this.resourceUrl}/${this.getGeneralrulesIdentifier(generalrules)}`, generalrules, {
      observe: 'response',
    });
  }

  partialUpdate(generalrules: PartialUpdateGeneralrules): Observable<EntityResponseType> {
    return this.http.patch<IGeneralrules>(`${this.resourceUrl}/${this.getGeneralrulesIdentifier(generalrules)}`, generalrules, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IGeneralrules>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGeneralrules[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGeneralrulesIdentifier(generalrules: Pick<IGeneralrules, 'id'>): string {
    return generalrules.id;
  }

  compareGeneralrules(o1: Pick<IGeneralrules, 'id'> | null, o2: Pick<IGeneralrules, 'id'> | null): boolean {
    return o1 && o2 ? this.getGeneralrulesIdentifier(o1) === this.getGeneralrulesIdentifier(o2) : o1 === o2;
  }

  addGeneralrulesToCollectionIfMissing<Type extends Pick<IGeneralrules, 'id'>>(
    generalrulesCollection: Type[],
    ...generalrulesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const generalrules: Type[] = generalrulesToCheck.filter(isPresent);
    if (generalrules.length > 0) {
      const generalrulesCollectionIdentifiers = generalrulesCollection.map(
        generalrulesItem => this.getGeneralrulesIdentifier(generalrulesItem)!
      );
      const generalrulesToAdd = generalrules.filter(generalrulesItem => {
        const generalrulesIdentifier = this.getGeneralrulesIdentifier(generalrulesItem);
        if (generalrulesCollectionIdentifiers.includes(generalrulesIdentifier)) {
          return false;
        }
        generalrulesCollectionIdentifiers.push(generalrulesIdentifier);
        return true;
      });
      return [...generalrulesToAdd, ...generalrulesCollection];
    }
    return generalrulesCollection;
  }
}
