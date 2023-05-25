import { IEducationlevel, NewEducationlevel } from './educationlevel.model';

export const sampleWithRequiredData: IEducationlevel = {
  id: '49121c71-02bc-468f-98db-416e84f76aee',
  designation: 'online',
};

export const sampleWithPartialData: IEducationlevel = {
  id: '89155d93-619b-460c-af42-23759c7ce9d2',
  designation: 'purple models',
};

export const sampleWithFullData: IEducationlevel = {
  id: 'b293a00d-2703-4dad-974f-8748b6ddf5b8',
  designation: 'Montana',
};

export const sampleWithNewData: NewEducationlevel = {
  designation: 'Rustic Agent Internal',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
