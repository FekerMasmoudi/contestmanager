import { IContestfield, NewContestfield } from './contestfield.model';

export const sampleWithRequiredData: IContestfield = {
  id: '35541e9f-02a7-4d9f-94c7-3c9fedc12100',
  reference: 20741,
  fopconstraint: 'cyan',
  fvalue: 'mint mesh Granite',
};

export const sampleWithPartialData: IContestfield = {
  id: '3b40ea80-d4cd-41b7-9f29-e4f0469cefbe',
  reference: 98090,
  fopconstraint: 'Functionality Wooden collaborative',
  fvalue: 'Associate Cotton',
  sopconstraint: 'synthesize redundant Handcrafted',
};

export const sampleWithFullData: IContestfield = {
  id: '38468394-9e57-41bc-b0a7-f5dee9e36019',
  reference: 98001,
  mandatory: false,
  fopconstraint: 'Swaziland Communications emulation',
  fvalue: 'Jewelery HDD parsing',
  sopconstraint: 'JBOD green',
  svalue: 'Buckinghamshire infomediaries redundant',
};

export const sampleWithNewData: NewContestfield = {
  reference: 50937,
  fopconstraint: 'SDD Tennessee Dynamic',
  fvalue: 'program',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
