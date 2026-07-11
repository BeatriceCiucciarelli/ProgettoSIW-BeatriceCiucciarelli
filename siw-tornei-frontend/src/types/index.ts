// Interfacce TypeScript che replicano i DTO Java del backend
// (stessi campi, stessi tipi -- se il backend cambia, il compilatore
// TypeScript segnala subito tutti i punti da aggiornare)

export interface ClassificaRiga {
  squadraId: number;
  squadraNome: string;
  punti: number;
  partiteGiocate: number;
  vittorie: number;
  pareggi: number;
  sconfitte: number;
  golFatti: number;
  golSubiti: number;
  differenzaReti: number;
}

export interface TorneoSummary {
  id: number;
  nome: string;
  anno: number;
}
