import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContestform, NewContestform } from '../contestform.model';

export type PartialUpdateContestform = Partial<IContestform> & Pick<IContestform, 'id'>;

export type EntityResponseType = HttpResponse<IContestform>;
export type EntityArrayResponseType = HttpResponse<IContestform[]>;

@Injectable({ providedIn: 'root' })
export class ContestformService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contestforms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(contestform: NewContestform): Observable<EntityResponseType> {
    return this.http.post<IContestform>(this.resourceUrl, contestform, { observe: 'response' });
  }

  update(contestform: IContestform): Observable<EntityResponseType> {
    return this.http.put<IContestform>(`${this.resourceUrl}/${this.getContestformIdentifier(contestform)}`, contestform, {
      observe: 'response',
    });
  }

  partialUpdate(contestform: PartialUpdateContestform): Observable<EntityResponseType> {
    return this.http.patch<IContestform>(`${this.resourceUrl}/${this.getContestformIdentifier(contestform)}`, contestform, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IContestform>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContestform[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContestformIdentifier(contestform: Pick<IContestform, 'id'>): string {
    return contestform.id;
  }

  compareContestform(o1: Pick<IContestform, 'id'> | null, o2: Pick<IContestform, 'id'> | null): boolean {
    return o1 && o2 ? this.getContestformIdentifier(o1) === this.getContestformIdentifier(o2) : o1 === o2;
  }

  addContestformToCollectionIfMissing<Type extends Pick<IContestform, 'id'>>(
    contestformCollection: Type[],
    ...contestformsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const contestforms: Type[] = contestformsToCheck.filter(isPresent);
    if (contestforms.length > 0) {
      const contestformCollectionIdentifiers = contestformCollection.map(
        contestformItem => this.getContestformIdentifier(contestformItem)!
      );
      const contestformsToAdd = contestforms.filter(contestformItem => {
        const contestformIdentifier = this.getContestformIdentifier(contestformItem);
        if (contestformCollectionIdentifiers.includes(contestformIdentifier)) {
          return false;
        }
        contestformCollectionIdentifiers.push(contestformIdentifier);
        return true;
      });
      return [...contestformsToAdd, ...contestformCollection];
    }
    return contestformCollection;
  }
}
