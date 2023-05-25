import dayjs from 'dayjs/esm';
import { IGeneralrules } from 'app/entities/generalrules/generalrules.model';

export interface IContestannounce {
  id: string;
  announceText?: string | null;
  startdate?: dayjs.Dayjs | null;
  limitdate?: dayjs.Dayjs | null;
  idsgeneralrules?: Pick<IGeneralrules, 'id'> | null;
}

export type NewContestannounce = Omit<IContestannounce, 'id'> & { id: null };
