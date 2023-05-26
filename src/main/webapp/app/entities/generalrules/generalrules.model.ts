import dayjs from 'dayjs/esm';

export interface IGeneralrules {
  id: string;
  designation?: string | null;
  effectdate?: dayjs.Dayjs | null;
}

export type NewGeneralrules = Omit<IGeneralrules, 'id'> & { id: null };
