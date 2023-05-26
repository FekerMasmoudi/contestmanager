import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContestannounce, NewContestannounce } from '../contestannounce.model';

export type PartialUpdateContestannounce = Partial<IContestannounce> & Pick<IContestannounce, 'id'>;

type RestOf<T extends IContestannounce | NewContestannounce> = Omit<T, 'startdate' | 'limitdate'> & {
  startdate?: string | null;
  limitdate?: string | null;
};

export type RestContestannounce = RestOf<IContestannounce>;

export type NewRestContestannounce = RestOf<NewContestannounce>;

export type PartialUpdateRestContestannounce = RestOf<PartialUpdateContestannounce>;

export type EntityResponseType = HttpResponse<IContestannounce>;
export type EntityArrayResponseType = HttpResponse<IContestannounce[]>;

@Injectable({ providedIn: 'root' })
export class ContestannounceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contestannounces');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(contestannounce: NewContestannounce): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contestannounce);
    return this.http
      .post<RestContestannounce>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(contestannounce: IContestannounce): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contestannounce);
    return this.http
      .put<RestContestannounce>(`${this.resourceUrl}/${this.getContestannounceIdentifier(contestannounce)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(contestannounce: PartialUpdateContestannounce): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contestannounce);
    return this.http
      .patch<RestContestannounce>(`${this.resourceUrl}/${this.getContestannounceIdentifier(contestannounce)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestContestannounce>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestContestannounce[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContestannounceIdentifier(contestannounce: Pick<IContestannounce, 'id'>): string {
    return contestannounce.id;
  }

  compareContestannounce(o1: Pick<IContestannounce, 'id'> | null, o2: Pick<IContestannounce, 'id'> | null): boolean {
    return o1 && o2 ? this.getContestannounceIdentifier(o1) === this.getContestannounceIdentifier(o2) : o1 === o2;
  }

  addContestannounceToCollectionIfMissing<Type extends Pick<IContestannounce, 'id'>>(
    contestannounceCollection: Type[],
    ...contestannouncesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const contestannounces: Type[] = contestannouncesToCheck.filter(isPresent);
    if (contestannounces.length > 0) {
      const contestannounceCollectionIdentifiers = contestannounceCollection.map(
        contestannounceItem => this.getContestannounceIdentifier(contestannounceItem)!
      );
      const contestannouncesToAdd = contestannounces.filter(contestannounceItem => {
        const contestannounceIdentifier = this.getContestannounceIdentifier(contestannounceItem);
        if (contestannounceCollectionIdentifiers.includes(contestannounceIdentifier)) {
          return false;
        }
        contestannounceCollectionIdentifiers.push(contestannounceIdentifier);
        return true;
      });
      return [...contestannouncesToAdd, ...contestannounceCollection];
    }
    return contestannounceCollection;
  }

  protected convertDateFromClient<T extends IContestannounce | NewContestannounce | PartialUpdateContestannounce>(
    contestannounce: T
  ): RestOf<T> {
    return {
      ...contestannounce,
      startdate: contestannounce.startdate?.format(DATE_FORMAT) ?? null,
      limitdate: contestannounce.limitdate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restContestannounce: RestContestannounce): IContestannounce {
    return {
      ...restContestannounce,
      startdate: restContestannounce.startdate ? dayjs(restContestannounce.startdate) : undefined,
      limitdate: restContestannounce.limitdate ? dayjs(restContestannounce.limitdate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestContestannounce>): HttpResponse<IContestannounce> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestContestannounce[]>): HttpResponse<IContestannounce[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
