export interface IGrade {
  id: string;
  designation?: string | null;
}

export type NewGrade = Omit<IGrade, 'id'> & { id: null };
