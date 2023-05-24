import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFields, NewFields } from '../fields.model';

export type PartialUpdateFields = Partial<IFields> & Pick<IFields, 'id'>;

export type EntityResponseType = HttpResponse<IFields>;
export type EntityArrayResponseType = HttpResponse<IFields[]>;

@Injectable({ providedIn: 'root' })
export class FieldsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fields');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fields: NewFields): Observable<EntityResponseType> {
    return this.http.post<IFields>(this.resourceUrl, fields, { observe: 'response' });
  }

  update(fields: IFields): Observable<EntityResponseType> {
    return this.http.put<IFields>(`${this.resourceUrl}/${this.getFieldsIdentifier(fields)}`, fields, { observe: 'response' });
  }

  partialUpdate(fields: PartialUpdateFields): Observable<EntityResponseType> {
    return this.http.patch<IFields>(`${this.resourceUrl}/${this.getFieldsIdentifier(fields)}`, fields, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IFields>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFields[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFieldsIdentifier(fields: Pick<IFields, 'id'>): string {
    return fields.id;
  }

  compareFields(o1: Pick<IFields, 'id'> | null, o2: Pick<IFields, 'id'> | null): boolean {
    return o1 && o2 ? this.getFieldsIdentifier(o1) === this.getFieldsIdentifier(o2) : o1 === o2;
  }

  addFieldsToCollectionIfMissing<Type extends Pick<IFields, 'id'>>(
    fieldsCollection: Type[],
    ...fieldsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fields: Type[] = fieldsToCheck.filter(isPresent);
    if (fields.length > 0) {
      const fieldsCollectionIdentifiers = fieldsCollection.map(fieldsItem => this.getFieldsIdentifier(fieldsItem)!);
      const fieldsToAdd = fields.filter(fieldsItem => {
        const fieldsIdentifier = this.getFieldsIdentifier(fieldsItem);
        if (fieldsCollectionIdentifiers.includes(fieldsIdentifier)) {
          return false;
        }
        fieldsCollectionIdentifiers.push(fieldsIdentifier);
        return true;
      });
      return [...fieldsToAdd, ...fieldsCollection];
    }
    return fieldsCollection;
  }
}
