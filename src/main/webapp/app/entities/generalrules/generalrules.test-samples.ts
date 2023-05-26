import dayjs from 'dayjs/esm';

import { IGeneralrules, NewGeneralrules } from './generalrules.model';

export const sampleWithRequiredData: IGeneralrules = {
  id: '6c9ef561-3195-4a2b-b2e8-ef1a9f9b1602',
  designation: 'Kids',
  effectdate: dayjs('2023-05-25'),
};

export const sampleWithPartialData: IGeneralrules = {
  id: '761ae444-6a6e-47fe-8abb-00996ae1444d',
  designation: 'Rwanda',
  effectdate: dayjs('2023-05-25'),
};

export const sampleWithFullData: IGeneralrules = {
  id: '37f1ebec-93a5-487c-90c2-e4a8d46cc965',
  designation: 'Savings Reactive Rustic',
  effectdate: dayjs('2023-05-25'),
};

export const sampleWithNewData: NewGeneralrules = {
  designation: 'markets Republic',
  effectdate: dayjs('2023-05-24'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
