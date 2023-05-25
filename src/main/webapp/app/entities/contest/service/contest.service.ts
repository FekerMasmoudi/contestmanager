import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContest, NewContest } from '../contest.model';

export type PartialUpdateContest = Partial<IContest> & Pick<IContest, 'id'>;

export type EntityResponseType = HttpResponse<IContest>;
export type EntityArrayResponseType = HttpResponse<IContest[]>;

@Injectable({ providedIn: 'root' })
export class ContestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(contest: NewContest): Observable<EntityResponseType> {
    return this.http.post<IContest>(this.resourceUrl, contest, { observe: 'response' });
  }

  update(contest: IContest): Observable<EntityResponseType> {
    return this.http.put<IContest>(`${this.resourceUrl}/${this.getContestIdentifier(contest)}`, contest, { observe: 'response' });
  }

  partialUpdate(contest: PartialUpdateContest): Observable<EntityResponseType> {
    return this.http.patch<IContest>(`${this.resourceUrl}/${this.getContestIdentifier(contest)}`, contest, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IContest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContestIdentifier(contest: Pick<IContest, 'id'>): string {
    return contest.id;
  }

  compareContest(o1: Pick<IContest, 'id'> | null, o2: Pick<IContest, 'id'> | null): boolean {
    return o1 && o2 ? this.getContestIdentifier(o1) === this.getContestIdentifier(o2) : o1 === o2;
  }

  addContestToCollectionIfMissing<Type extends Pick<IContest, 'id'>>(
    contestCollection: Type[],
    ...contestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const contests: Type[] = contestsToCheck.filter(isPresent);
    if (contests.length > 0) {
      const contestCollectionIdentifiers = contestCollection.map(contestItem => this.getContestIdentifier(contestItem)!);
      const contestsToAdd = contests.filter(contestItem => {
        const contestIdentifier = this.getContestIdentifier(contestItem);
        if (contestCollectionIdentifiers.includes(contestIdentifier)) {
          return false;
        }
        contestCollectionIdentifiers.push(contestIdentifier);
        return true;
      });
      return [...contestsToAdd, ...contestCollection];
    }
    return contestCollection;
  }
}
