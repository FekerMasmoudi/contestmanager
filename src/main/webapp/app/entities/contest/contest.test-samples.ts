import { IContest, NewContest } from './contest.model';

export const sampleWithRequiredData: IContest = {
  id: '83c83c71-dde1-4d84-9f90-443d30771c0d',
  name: 'index invoice Gardens',
  parent: 'attitude-oriented Shirt state',
  description: 'Green Assurance Metal',
};

export const sampleWithPartialData: IContest = {
  id: '4e160267-65cf-4955-9533-07257df3b4dc',
  name: 'Future deposit Belgium',
  parent: 'application Principal Rustic',
  description: 'Unbranded RAM programming',
};

export const sampleWithFullData: IContest = {
  id: 'c48246ce-3d73-4b9c-a322-8712bc43d694',
  name: 'Steel back-end backing',
  parent: 'e-enable Dollar Wyoming',
  description: 'group',
};

export const sampleWithNewData: NewContest = {
  name: 'object-oriented',
  parent: 'Lesotho Republic',
  description: 'XSS bus transmitting',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
