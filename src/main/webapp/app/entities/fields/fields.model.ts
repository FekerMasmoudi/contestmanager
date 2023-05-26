import { IContestform } from 'app/entities/contestform/contestform.model';
import { etype } from 'app/entities/enumerations/etype.model';

export interface IFields {
  id: string;
  name?: string | null;
  ftype?: etype | null;
  fvalue?: string | null;
  contestform?: Pick<IContestform, 'id'> | null;
}

export type NewFields = Omit<IFields, 'id'> & { id: null };
