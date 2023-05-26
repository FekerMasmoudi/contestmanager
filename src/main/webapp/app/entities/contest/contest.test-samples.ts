import { IContest, NewContest } from './contest.model';

export const sampleWithRequiredData: IContest = {
  id: '83c83c71-dde1-4d84-9f90-443d30771c0d',
  name: 'index invoice Gardens',
  parent: 'attitude-oriented Shirt state',
  description: 'Green Assurance Metal',
  nbpositions: 29084,
  status: true,
};

export const sampleWithPartialData: IContest = {
  id: '16026765-cf95-45d5-b307-257df3b4dcfe',
  name: 'bluetooth',
  parent: 'Belgium USB Cambridgeshire',
  description: 'Rustic',
  nbpositions: 67824,
  status: false,
};

export const sampleWithFullData: IContest = {
  id: 'e4e1ede2-c482-446c-a3d7-3b9ca3228712',
  name: 'copy navigating User-friendly',
  parent: 'FTP',
  description: 'backing',
  nbpositions: 83423,
  status: false,
};

export const sampleWithNewData: NewContest = {
  name: 'calculating multi-byte intuitive',
  parent: 'Functionality protocol',
  description: 'Bedfordshire Prairie',
  nbpositions: 85558,
  status: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
