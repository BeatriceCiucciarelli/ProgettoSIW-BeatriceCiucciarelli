import api from "./api";
import type { ClassificaRiga, TorneoSummary } from "../types";

// I componenti chiamano queste funzioni, non fanno mai api.get(...) direttamente

export async function getTornei(): Promise<TorneoSummary[]> {
  const { data } = await api.get<TorneoSummary[]>("/tornei");
  return data;
}

export async function getClassifica(torneoId: number): Promise<ClassificaRiga[]> {
  const { data } = await api.get<ClassificaRiga[]>(`/tornei/${torneoId}/classifica`);
  return data;
}
