import { useEffect, useState } from "react";
import "./App.css";
import ClassificaTable from "./components/ClassificaTable";
import { getClassifica, getTornei } from "./services/torneoService";
import type { ClassificaRiga, TorneoSummary } from "./types";

function App() {
  const [tornei, setTornei] = useState<TorneoSummary[]>([]);
  const [torneoSelezionato, setTorneoSelezionato] = useState<number | null>(null);
  const [classifica, setClassifica] = useState<ClassificaRiga[]>([]);
  const [caricamentoTornei, setCaricamentoTornei] = useState(true);
  const [caricamentoClassifica, setCaricamentoClassifica] = useState(false);
  const [errore, setErrore] = useState<string | null>(null);

  // Carica l'elenco dei tornei una sola volta, al mount del componente
  useEffect(() => {
    getTornei()
      .then((dati) => {
        setTornei(dati);
        setCaricamentoTornei(false);
        if (dati.length > 0) {
          setTorneoSelezionato(dati[0].id);
        }
      })
      .catch((err) => {
        setErrore("Errore nel caricamento dei tornei: " + err.message);
        setCaricamentoTornei(false);
      });
  }, []);

  // Ricarica la classifica ogni volta che cambia il torneo selezionato
  useEffect(() => {
    if (torneoSelezionato === null) {
      return;
    }

    setCaricamentoClassifica(true);
    setErrore(null);

    getClassifica(torneoSelezionato)
      .then((dati) => {
        setClassifica(dati);
        setCaricamentoClassifica(false);
      })
      .catch((err) => {
        setErrore("Errore nel caricamento della classifica: " + err.message);
        setCaricamentoClassifica(false);
      });
  }, [torneoSelezionato]);

  if (caricamentoTornei) {
    return <p>Caricamento tornei...</p>;
  }

  return (
    <div style={{ padding: "2rem", fontFamily: "sans-serif" }}>
      <h1>Classifica Torneo</h1>

      <div style={{ marginBottom: "1rem" }}>
        <label htmlFor="torneo-select">Torneo: </label>
        <select
          id="torneo-select"
          value={torneoSelezionato ?? ""}
          onChange={(e) => setTorneoSelezionato(Number(e.target.value))}
        >
          {tornei.map((torneo) => (
            <option key={torneo.id} value={torneo.id}>
              {torneo.nome} ({torneo.anno})
            </option>
          ))}
        </select>
      </div>

      {errore && <p style={{ color: "red" }}>{errore}</p>}

      {caricamentoClassifica ? (
        <p>Caricamento classifica...</p>
      ) : (
        <ClassificaTable righe={classifica} />
      )}
    </div>
  );
}

export default App;
