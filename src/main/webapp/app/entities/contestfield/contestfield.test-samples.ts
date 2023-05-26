import { IContestfield, NewContestfield } from './contestfield.model';

export const sampleWithRequiredData: IContestfield = {
  id: '35541e9f-02a7-4d9f-94c7-3c9fedc12100',
  reference: 20741,
  fopconstraint: 'cyan',
  fvalue: 'mint mesh Granite',
  ctype: 'Investment port',
  cname: 'payment compressing RAM',
};

export const sampleWithPartialData: IContestfield = {
  id: '9e4f0469-cefb-4efa-b4c2-275f39f0c168',
  reference: 84940,
  fopconstraint: 'Nakfa facilitate payment',
  fvalue: 'fresh-thinking Persevering',
  sopconstraint: 'FTP Handcrafted',
  svalue: 'Generic programming Vermont',
  ctype: 'Ergonomic drive',
  cname: 'Communications emulation',
};

export const sampleWithFullData: IContestfield = {
  id: 'f0c8d92d-fd6e-4f32-8cc7-b06afc6d8ed1',
  reference: 13004,
  mandatory: true,
  fopconstraint: 'iterate Avon JSON',
  fvalue: 'Virtual Practical Assistant',
  sopconstraint: 'schemas Buckinghamshire',
  svalue: 'focus Clothing',
  logic: 'convergence monitor',
  ctype: 'Principal',
  cname: 'infrastructures',
};

export const sampleWithNewData: NewContestfield = {
  reference: 45181,
  fopconstraint: 'Massachusetts silver Specialist',
  fvalue: 'invoice Architect Baby',
  ctype: 'Exclusive haptic Zambia',
  cname: 'streamline invoice benchmark',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
