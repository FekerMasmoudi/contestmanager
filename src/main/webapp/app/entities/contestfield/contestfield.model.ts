import { IContest } from 'app/entities/contest/contest.model';

export interface IContestfield {
  id: string;
  reference?: number | null;
  mandatory?: boolean | null;
  fopconstraint?: string | null;
  fvalue?: string | null;
  sopconstraint?: string | null;
  svalue?: string | null;
  idcontest?: Pick<IContest, 'id'> | null;
}

export type NewContestfield = Omit<IContestfield, 'id'> & { id: null };
