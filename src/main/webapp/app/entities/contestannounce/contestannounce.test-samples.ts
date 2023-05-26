import dayjs from 'dayjs/esm';

import { IContestannounce, NewContestannounce } from './contestannounce.model';

export const sampleWithRequiredData: IContestannounce = {
  id: 'f23c648a-205c-4ee0-a281-2a8645c7524b',
  announceText: 'Way card',
  startdate: dayjs('2023-05-25'),
  limitdate: dayjs('2023-05-25'),
  status: true,
};

export const sampleWithPartialData: IContestannounce = {
  id: '7482e769-528b-48e7-942f-03b2d15f5d10',
  announceText: 'revolutionary Intelligent',
  startdate: dayjs('2023-05-25'),
  limitdate: dayjs('2023-05-25'),
  status: false,
};

export const sampleWithFullData: IContestannounce = {
  id: 'ca7567e1-f911-4284-87b8-d508e33e7e76',
  announceText: 'Court',
  startdate: dayjs('2023-05-25'),
  limitdate: dayjs('2023-05-24'),
  status: true,
};

export const sampleWithNewData: NewContestannounce = {
  announceText: 'impactful relationships Granite',
  startdate: dayjs('2023-05-25'),
  limitdate: dayjs('2023-05-25'),
  status: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
