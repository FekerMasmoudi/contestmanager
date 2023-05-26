import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGeneralrules, NewGeneralrules } from '../generalrules.model';

export type PartialUpdateGeneralrules = Partial<IGeneralrules> & Pick<IGeneralrules, 'id'>;

type RestOf<T extends IGeneralrules | NewGeneralrules> = Omit<T, 'effectdate'> & {
  effectdate?: string | null;
};

export type RestGeneralrules = RestOf<IGeneralrules>;

export type NewRestGeneralrules = RestOf<NewGeneralrules>;

export type PartialUpdateRestGeneralrules = RestOf<PartialUpdateGeneralrules>;

export type EntityResponseType = HttpResponse<IGeneralrules>;
export type EntityArrayResponseType = HttpResponse<IGeneralrules[]>;

@Injectable({ providedIn: 'root' })
export class GeneralrulesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/generalrules');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(generalrules: NewGeneralrules): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(generalrules);
    return this.http
      .post<RestGeneralrules>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(generalrules: IGeneralrules): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(generalrules);
    return this.http
      .put<RestGeneralrules>(`${this.resourceUrl}/${this.getGeneralrulesIdentifier(generalrules)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(generalrules: PartialUpdateGeneralrules): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(generalrules);
    return this.http
      .patch<RestGeneralrules>(`${this.resourceUrl}/${this.getGeneralrulesIdentifier(generalrules)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestGeneralrules>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestGeneralrules[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
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

  protected convertDateFromClient<T extends IGeneralrules | NewGeneralrules | PartialUpdateGeneralrules>(generalrules: T): RestOf<T> {
    return {
      ...generalrules,
      effectdate: generalrules.effectdate?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restGeneralrules: RestGeneralrules): IGeneralrules {
    return {
      ...restGeneralrules,
      effectdate: restGeneralrules.effectdate ? dayjs(restGeneralrules.effectdate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestGeneralrules>): HttpResponse<IGeneralrules> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestGeneralrules[]>): HttpResponse<IGeneralrules[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
