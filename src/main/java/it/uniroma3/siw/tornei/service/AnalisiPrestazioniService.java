package it.uniroma3.siw.tornei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.tornei.dto.RisultatoAnalisi;
import it.uniroma3.siw.tornei.model.Partita;
import it.uniroma3.siw.tornei.repository.PartitaRepository;

import jakarta.persistence.EntityManager;

/*
 * Analisi sperimentale richiesta dalla Sezione 8.2 della traccia:
 * confronto tra diverse strategie di accesso ai dati (LAZY/EAGER di
 * default, JOIN FETCH, EntityGraph) sul caso d'uso "elenco delle partite
 * di un torneo con le rispettive squadre home/away" (lo stesso dato usato
 * per calcolare la classifica).
 *
 * Le due metriche misurate sono:
 * - numero di query SQL eseguite (dalle statistiche interne di Hibernate:
 *   e' la prova oggettiva del problema N+1, indipendente dal rumore di
 *   misurazione)
 * - tempo di esecuzione in millisecondi (indicativo, ma su pochi dati di
 *   test puo' essere poco significativo/rumoroso: vedi discussione)
 */
@Service
public class AnalisiPrestazioniService {

    private final PartitaRepository partitaRepository;
    private final EntityManager entityManager;

    public AnalisiPrestazioniService(PartitaRepository partitaRepository, EntityManager entityManager) {
        this.partitaRepository = partitaRepository;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<RisultatoAnalisi> confrontaStrategie(Long torneoId) {
        Statistics statistiche = entityManager.unwrap(Session.class)
            .getSessionFactory()
            .getStatistics();
        statistiche.setStatisticsEnabled(true);

        List<RisultatoAnalisi> risultati = new ArrayList<>();

        risultati.add(misura("1) Query derivata (baseline, N+1)", () -> {
            List<Partita> partite = this.partitaRepository.findByTorneoId(torneoId);
            // costringiamo Hibernate ad accedere davvero alle associazioni,
            // altrimenti con proxy LAZY non misureremmo nulla
            for (Partita p : partite) {
                p.getSquadraHome().getNome();
                p.getSquadraAway().getNome();
            }
            return partite.size();
        }, statistiche));

        risultati.add(misura("2) JOIN FETCH (JPQL)", () -> {
            List<Partita> partite = this.partitaRepository.findByTorneoIdJoinFetch(torneoId);
            for (Partita p : partite) {
                p.getSquadraHome().getNome();
                p.getSquadraAway().getNome();
            }
            return partite.size();
        }, statistiche));

        risultati.add(misura("3) @EntityGraph", () -> {
            List<Partita> partite = this.partitaRepository.findAllByTorneoId(torneoId);
            for (Partita p : partite) {
                p.getSquadraHome().getNome();
                p.getSquadraAway().getNome();
            }
            return partite.size();
        }, statistiche));

        return risultati;
    }

    private RisultatoAnalisi misura(String nomeStrategia, Supplier<Integer> operazione, Statistics statistiche) {
        // pulisce la persistence context: altrimenti Hibernate riutilizza le
        // entita' gia' caricate dalla strategia precedente e falsa sia il
        // conteggio delle query sia i tempi
        this.entityManager.clear();

        long queryPrima = statistiche.getPrepareStatementCount();
        long inizioNano = System.nanoTime();

        int numeroRisultati = operazione.get();

        long fineNano = System.nanoTime();
        long queryDopo = statistiche.getPrepareStatementCount();

        RisultatoAnalisi risultato = new RisultatoAnalisi();
        risultato.setStrategia(nomeStrategia);
        risultato.setNumeroPartite(numeroRisultati);
        risultato.setNumeroQuery(queryDopo - queryPrima);
        risultato.setTempoMillisecondi((fineNano - inizioNano) / 1_000_000.0);

        return risultato;
    }
}