import { ISpeciality, NewSpeciality } from './speciality.model';

export const sampleWithRequiredData: ISpeciality = {
  id: '5b6a3d21-bdf7-443c-8429-78df2d6a0f40',
  designation: 'online Refined Strategist',
};

export const sampleWithPartialData: ISpeciality = {
  id: '8d9bef7a-b8a9-4292-81ce-7ff98f6cd8a4',
  designation: 'Direct',
};

export const sampleWithFullData: ISpeciality = {
  id: '2ce59fee-889c-4189-8651-8496f10bb4d9',
  designation: 'generating',
};

export const sampleWithNewData: NewSpeciality = {
  designation: 'visualize synthesize',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
