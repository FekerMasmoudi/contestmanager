import { IContestannounce } from 'app/entities/contestannounce/contestannounce.model';
import { IGrade } from 'app/entities/grade/grade.model';
import { ISpeciality } from 'app/entities/speciality/speciality.model';
import { ISector } from 'app/entities/sector/sector.model';

export interface IContest {
  id: string;
  name?: string | null;
  parent?: string | null;
  description?: string | null;
  nbpositions?: number | null;
  status?: boolean | null;
  idcontestannounce?: Pick<IContestannounce, 'id'> | null;
  idgrade?: Pick<IGrade, 'id'> | null;
  idspeciality?: Pick<ISpeciality, 'id'> | null;
  idsector?: Pick<ISector, 'id'> | null;
}

export type NewContest = Omit<IContest, 'id'> & { id: null };
