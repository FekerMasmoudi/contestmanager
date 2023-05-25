export interface ISpeciality {
  id: string;
  designation?: string | null;
}

export type NewSpeciality = Omit<ISpeciality, 'id'> & { id: null };
