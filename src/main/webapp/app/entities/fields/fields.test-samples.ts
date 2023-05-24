import { etype } from 'app/entities/enumerations/etype.model';

import { IFields, NewFields } from './fields.model';

export const sampleWithRequiredData: IFields = {
  id: '0622da08-62d3-4dcb-bb0b-e492d02d34f7',
  name: 'Market Steel',
  ftype: etype['DATE'],
};

export const sampleWithPartialData: IFields = {
  id: '3cbdf4b8-50ea-40f6-808e-32da67ac7730',
  name: 'Concrete edge',
  ftype: etype['FILE'],
};

export const sampleWithFullData: IFields = {
  id: 'd70b67cf-afa2-46e7-95e5-8833a096bfcb',
  name: 'success',
  ftype: etype['STRING'],
  fvalue: 'intuitive copying',
};

export const sampleWithNewData: NewFields = {
  name: 'Realigned Unbranded azure',
  ftype: etype['FILE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
