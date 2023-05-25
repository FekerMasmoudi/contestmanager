import dayjs from 'dayjs/esm';

import { IContestannounce, NewContestannounce } from './contestannounce.model';

export const sampleWithRequiredData: IContestannounce = {
  id: 'f23c648a-205c-4ee0-a281-2a8645c7524b',
  announceText: 'Way card',
  startdate: dayjs('2023-05-23'),
  limitdate: dayjs('2023-05-23'),
};

export const sampleWithPartialData: IContestannounce = {
  id: 'f7482e76-9528-4b8e-b542-f03b2d15f5d1',
  announceText: 'Hill',
  startdate: dayjs('2023-05-24'),
  limitdate: dayjs('2023-05-24'),
};

export const sampleWithFullData: IContestannounce = {
  id: '3e190ca7-567e-41f9-9128-487b8d508e33',
  announceText: 'Cambridgeshire integrate Fantastic',
  startdate: dayjs('2023-05-23'),
  limitdate: dayjs('2023-05-23'),
};

export const sampleWithNewData: NewContestannounce = {
  announceText: 'impactful relationships Granite',
  startdate: dayjs('2023-05-24'),
  limitdate: dayjs('2023-05-24'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
