import type { ClassificaRiga } from "../types";

interface ClassificaTableProps {
  righe: ClassificaRiga[];
}

// Componente "dumb": riceve solo props, non fa fetch, non ha stato proprio.
// Tutta la logica di caricamento dati sta nel componente padre (App.tsx).
function ClassificaTable({ righe }: ClassificaTableProps) {
  if (righe.length === 0) {
    return <p>Nessuna squadra iscritta a questo torneo.</p>;
  }

  return (
    <table border={1} cellPadding={6}>
      <thead>
        <tr>
          <th>#</th>
          <th>Squadra</th>
          <th>Punti</th>
          <th>G</th>
          <th>V</th>
          <th>N</th>
          <th>P</th>
          <th>GF</th>
          <th>GS</th>
          <th>DR</th>
        </tr>
      </thead>
      <tbody>
        {righe.map((riga, indice) => (
          <tr key={riga.squadraId}>
            <td>{indice + 1}</td>
            <td>{riga.squadraNome}</td>
            <td>{riga.punti}</td>
            <td>{riga.partiteGiocate}</td>
            <td>{riga.vittorie}</td>
            <td>{riga.pareggi}</td>
            <td>{riga.sconfitte}</td>
            <td>{riga.golFatti}</td>
            <td>{riga.golSubiti}</td>
            <td>{riga.differenzaReti}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default ClassificaTable;
