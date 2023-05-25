export interface IGeneralrules {
  id: string;
  designation?: string | null;
}

export type NewGeneralrules = Omit<IGeneralrules, 'id'> & { id: null };
