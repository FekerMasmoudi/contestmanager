import { ISector, NewSector } from './sector.model';

export const sampleWithRequiredData: ISector = {
  id: '3aedc306-fac0-4533-99e0-7e3b9b553545',
  designation: 'Utah Keyboard override',
};

export const sampleWithPartialData: ISector = {
  id: '7c222299-6b9a-4ba8-90dc-c8755533c409',
  designation: 'Cambridgeshire',
};

export const sampleWithFullData: ISector = {
  id: '379e0fc0-3e91-4098-88f0-d473f11e404b',
  designation: 'Peso Nebraska',
};

export const sampleWithNewData: NewSector = {
  designation: 'auxiliary Automotive',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
