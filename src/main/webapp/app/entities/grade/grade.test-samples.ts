import { IGrade, NewGrade } from './grade.model';

export const sampleWithRequiredData: IGrade = {
  id: '092b8d81-a1a6-4c1f-82a4-ced53d18d34b',
  designation: 'synthesizing',
};

export const sampleWithPartialData: IGrade = {
  id: '0e79fa40-78aa-408f-8d3f-8abc50410116',
  designation: 'Corner lavender Guinea',
};

export const sampleWithFullData: IGrade = {
  id: '34209be2-2baf-4f71-9846-8a5df1ca987a',
  designation: 'overriding Washington',
};

export const sampleWithNewData: NewGrade = {
  designation: 'Human Marketing',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
