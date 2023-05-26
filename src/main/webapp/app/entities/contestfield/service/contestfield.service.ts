import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContestfield, NewContestfield } from '../contestfield.model';

export type PartialUpdateContestfield = Partial<IContestfield> & Pick<IContestfield, 'id'>;

export type EntityResponseType = HttpResponse<IContestfield>;
export type EntityArrayResponseType = HttpResponse<IContestfield[]>;

@Injectable({ providedIn: 'root' })
export class ContestfieldService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contestfields');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(contestfield: NewContestfield): Observable<EntityResponseType> {
    return this.http.post<IContestfield>(this.resourceUrl, contestfield, { observe: 'response' });
  }

  update(contestfield: IContestfield): Observable<EntityResponseType> {
    return this.http.put<IContestfield>(`${this.resourceUrl}/${this.getContestfieldIdentifier(contestfield)}`, contestfield, {
      observe: 'response',
    });
  }

  partialUpdate(contestfield: PartialUpdateContestfield): Observable<EntityResponseType> {
    return this.http.patch<IContestfield>(`${this.resourceUrl}/${this.getContestfieldIdentifier(contestfield)}`, contestfield, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IContestfield>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContestfield[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContestfieldIdentifier(contestfield: Pick<IContestfield, 'id'>): string {
    return contestfield.id;
  }

  compareContestfield(o1: Pick<IContestfield, 'id'> | null, o2: Pick<IContestfield, 'id'> | null): boolean {
    return o1 && o2 ? this.getContestfieldIdentifier(o1) === this.getContestfieldIdentifier(o2) : o1 === o2;
  }

  addContestfieldToCollectionIfMissing<Type extends Pick<IContestfield, 'id'>>(
    contestfieldCollection: Type[],
    ...contestfieldsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const contestfields: Type[] = contestfieldsToCheck.filter(isPresent);
    if (contestfields.length > 0) {
      const contestfieldCollectionIdentifiers = contestfieldCollection.map(
        contestfieldItem => this.getContestfieldIdentifier(contestfieldItem)!
      );
      const contestfieldsToAdd = contestfields.filter(contestfieldItem => {
        const contestfieldIdentifier = this.getContestfieldIdentifier(contestfieldItem);
        if (contestfieldCollectionIdentifiers.includes(contestfieldIdentifier)) {
          return false;
        }
        contestfieldCollectionIdentifiers.push(contestfieldIdentifier);
        return true;
      });
      return [...contestfieldsToAdd, ...contestfieldCollection];
    }
    return contestfieldCollection;
  }
}
