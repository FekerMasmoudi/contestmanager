import { IContestform, NewContestform } from './contestform.model';

export const sampleWithRequiredData: IContestform = {
  id: '6f106222-045d-45b0-9c02-0608fd1b6509',
};

export const sampleWithPartialData: IContestform = {
  id: 'befb701c-cc4a-4bee-bffa-057439f1093c',
};

export const sampleWithFullData: IContestform = {
  id: '186dd21e-161e-427d-af3e-30eabaf9a9ba',
};

export const sampleWithNewData: NewContestform = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
