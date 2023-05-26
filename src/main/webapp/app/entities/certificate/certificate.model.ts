export interface ICertificate {
  id: string;
  designation?: string | null;
}

export type NewCertificate = Omit<ICertificate, 'id'> & { id: null };
