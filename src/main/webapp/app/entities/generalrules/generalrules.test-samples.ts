import { IGeneralrules, NewGeneralrules } from './generalrules.model';

export const sampleWithRequiredData: IGeneralrules = {
  id: '6c9ef561-3195-4a2b-b2e8-ef1a9f9b1602',
};

export const sampleWithPartialData: IGeneralrules = {
  id: '0934761a-e444-46a6-a7fe-8abb00996ae1',
};

export const sampleWithFullData: IGeneralrules = {
  id: '444d08b5-237f-41eb-ac93-a587cd0c2e4a',
  designation: 'SQL neural',
};

export const sampleWithNewData: NewGeneralrules = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
