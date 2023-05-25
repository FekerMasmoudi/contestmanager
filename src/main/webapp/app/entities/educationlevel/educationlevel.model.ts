export interface IEducationlevel {
  id: string;
  designation?: string | null;
}

export type NewEducationlevel = Omit<IEducationlevel, 'id'> & { id: null };
