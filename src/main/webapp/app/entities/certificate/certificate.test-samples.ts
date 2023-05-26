import { ICertificate, NewCertificate } from './certificate.model';

export const sampleWithRequiredData: ICertificate = {
  id: '9d51e6ad-6470-4cdb-86b3-c3d5d6101283',
  designation: 'Toys Central',
};

export const sampleWithPartialData: ICertificate = {
  id: '70bf0914-7b53-4b75-9eaa-c074ad2040a8',
  designation: 'access Toys SDD',
};

export const sampleWithFullData: ICertificate = {
  id: '5b1a7cc1-7a36-48a1-ba2b-3caa1519a15d',
  designation: 'Qatari artificial',
};

export const sampleWithNewData: NewCertificate = {
  designation: 'Games',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
