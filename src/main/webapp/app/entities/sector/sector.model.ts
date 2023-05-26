export interface ISector {
  id: string;
  designation?: string | null;
}

export type NewSector = Omit<ISector, 'id'> & { id: null };
