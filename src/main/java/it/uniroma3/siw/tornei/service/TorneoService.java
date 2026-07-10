package it.uniroma3.siw.tornei.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.tornei.dto.RigaClassifica;
import it.uniroma3.siw.tornei.model.Partita;
import it.uniroma3.siw.tornei.model.Squadra;
import it.uniroma3.siw.tornei.model.Torneo;
import it.uniroma3.siw.tornei.repository.TorneoRepository;

@Service
public class TorneoService {

	private TorneoRepository torneoRepository;

	public TorneoService(TorneoRepository torneoRepository) {
		this.torneoRepository = torneoRepository;
	}

	@Transactional(readOnly = true)
    public List<Torneo> findAll() {
        List<Torneo> tornei = new ArrayList<>();
        this.torneoRepository.findAll().forEach(tornei::add);
        return tornei;
    }

    @Transactional(readOnly = true)
    public Optional<Torneo> findById(Long id) {
        return this.torneoRepository.findById(id);
    }

    @Transactional
    public Torneo salva(Torneo torneo) {
        return this.torneoRepository.save(torneo);
    }

    @Transactional
    public Torneo aggiorna(Long id, Torneo datiAggiornati) {
        Torneo torneo = this.torneoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Torneo non trovato con id " + id));

        torneo.setNome(datiAggiornati.getNome());
        torneo.setAnno(datiAggiornati.getAnno());
        torneo.setDescrizione(datiAggiornati.getDescrizione());

        return torneo;
    }

    /*
     * Caso d'uso: visualizzazione della classifica del torneo (Sezione 4.1).
     *
     * Regole:
     * - si parte da TUTTE le squadre iscritte al torneo (torneo.getSquadre()),
     *   anche quelle che non hanno ancora giocato (compaiono con 0 punti)
     * - si considerano solo le partite con stato PLAYED e risultato non nullo
     * - punteggio: vittoria = 3, pareggio = 1, sconfitta = 0
     * - ordinamento: punti desc, differenza reti desc, gol fatti desc, nome asc
     *
     * Operazione di sola lettura -> @Transactional(readOnly = true).
     *
     * NOTA SULLE PRESTAZIONI: questo metodo naviga torneo.getPartite() (relazione
     * LAZY) e per ciascuna partita accede a squadraHome/squadraAway (relazioni
     * EAGER di default su @ManyToOne). Con molte partite questo può generare un
     * problema N+1 query: e' il caso d'uso scelto per l'analisi sperimentale
     * richiesta dalla Sezione 8.2 della traccia (confronto LAZY/EAGER/join fetch).
     */
    @Transactional(readOnly = true)
    public List<RigaClassifica> calcolaClassifica(Long torneoId) {
        Torneo torneo = this.torneoRepository.findById(torneoId)
            .orElseThrow(() -> new IllegalArgumentException("Torneo non trovato con id " + torneoId));

        Map<Long, RigaClassifica> righePerSquadraId = new LinkedHashMap<>();
        for (Squadra squadra : torneo.getSquadre()) {
            RigaClassifica riga = new RigaClassifica();
            riga.setSquadra(squadra);
            righePerSquadraId.put(squadra.getId(), riga);
        }

        for (Partita partita : torneo.getPartite()) {
            boolean giocataConRisultato = partita.getStato() == Partita.StatoPartita.PLAYED
                && partita.getGoalsHome() != null
                && partita.getGoalsAway() != null;

            if (!giocataConRisultato) {
                continue;
            }

            RigaClassifica rigaHome = righePerSquadraId.get(partita.getSquadraHome().getId());
            RigaClassifica rigaAway = righePerSquadraId.get(partita.getSquadraAway().getId());

            // difesa: se per qualche motivo la squadra non risulta piu' iscritta
            // al torneo, semplicemente non la conteggiamo
            if (rigaHome == null || rigaAway == null) {
                continue;
            }

            int golHome = partita.getGoalsHome();
            int golAway = partita.getGoalsAway();

            rigaHome.setPartiteGiocate(rigaHome.getPartiteGiocate() + 1);
            rigaAway.setPartiteGiocate(rigaAway.getPartiteGiocate() + 1);

            rigaHome.setGolFatti(rigaHome.getGolFatti() + golHome);
            rigaHome.setGolSubiti(rigaHome.getGolSubiti() + golAway);
            rigaAway.setGolFatti(rigaAway.getGolFatti() + golAway);
            rigaAway.setGolSubiti(rigaAway.getGolSubiti() + golHome);

            if (golHome > golAway) {
                rigaHome.setVittorie(rigaHome.getVittorie() + 1);
                rigaHome.setPuntiTotali(rigaHome.getPuntiTotali() + 3);
                rigaAway.setSconfitte(rigaAway.getSconfitte() + 1);
            } else if (golHome < golAway) {
                rigaAway.setVittorie(rigaAway.getVittorie() + 1);
                rigaAway.setPuntiTotali(rigaAway.getPuntiTotali() + 3);
                rigaHome.setSconfitte(rigaHome.getSconfitte() + 1);
            } else {
                rigaHome.setPareggi(rigaHome.getPareggi() + 1);
                rigaAway.setPareggi(rigaAway.getPareggi() + 1);
                rigaHome.setPuntiTotali(rigaHome.getPuntiTotali() + 1);
                rigaAway.setPuntiTotali(rigaAway.getPuntiTotali() + 1);
            }
        }

        List<RigaClassifica> classifica = new ArrayList<>(righePerSquadraId.values());

        classifica.sort(
            Comparator.comparingInt(RigaClassifica::getPuntiTotali).reversed()
                .thenComparing(Comparator.comparingInt(RigaClassifica::getDifferenzaReti).reversed())
                .thenComparing(Comparator.comparingInt(RigaClassifica::getGolFatti).reversed())
                .thenComparing(riga -> riga.getSquadra().getNome())
        );

        return classifica;
    }
}