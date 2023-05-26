import { IEducationlevel } from 'app/entities/educationlevel/educationlevel.model';
import { ICertificate } from 'app/entities/certificate/certificate.model';

export interface IGrade {
  id: string;
  designation?: string | null;
  ideducationlevel?: Pick<IEducationlevel, 'id'> | null;
  idcertificate?: Pick<ICertificate, 'id'> | null;
}

export type NewGrade = Omit<IGrade, 'id'> & { id: null };
