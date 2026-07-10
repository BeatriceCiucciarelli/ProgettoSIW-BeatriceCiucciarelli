package it.uniroma3.siw.tornei.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.tornei.model.Squadra;
import it.uniroma3.siw.tornei.model.Torneo;
import it.uniroma3.siw.tornei.repository.SquadraRepository;

@Service
public class SquadraService {

    private final SquadraRepository squadraRepository;

    public SquadraService(SquadraRepository squadraRepository) {
        this.squadraRepository = squadraRepository;
    }

    @Transactional(readOnly = true)
    public List<Squadra> findAll() {
        List<Squadra> squadre = new ArrayList<>();
        this.squadraRepository.findAll().forEach(squadre::add);
        return squadre;
    }

    @Transactional(readOnly = true)
    public Optional<Squadra> findById(Long id) {
        return this.squadraRepository.findById(id);
    }

    @Transactional
    public Squadra salva(Squadra squadra) {
        return this.squadraRepository.save(squadra);
    }

    @Transactional
    public Squadra aggiorna(Long id, Squadra datiAggiornati) {
        Squadra squadra = this.squadraRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Squadra non trovata con id " + id));

        squadra.setNome(datiAggiornati.getNome());
        squadra.setCitta(datiAggiornati.getCitta());
        squadra.setAnnoFondazione(datiAggiornati.getAnnoFondazione());

        return squadra;
    }

    /*
     * Caso d'uso: eliminazione di una squadra (Sezione 4.3 - Amministratore).
     *
     * Due cose da gestire con attenzione:
     * 1) Se esistono partite (come home o away) che referenziano questa
     *    squadra, NON si puo' cancellare: la colonna squadra_home_id /
     *    squadra_away_id in Partita e' NOT NULL, quindi la cancellazione
     *    fallirebbe comunque a livello di DB con un vincolo di FK.
     *    Meglio intercettarlo prima con un messaggio chiaro.
     * 2) La relazione ManyToMany con Torneo va "scollegata" esplicitamente
     *    da ogni torneo associato, altrimenti restano righe orfane nella
     *    tabella di join torneo_squadra che violerebbero la FK.
     *    I giocatori invece si cancellano da soli grazie a
     *    cascade=ALL + orphanRemoval=true su Squadra.giocatori.
     */
    @Transactional
    public void elimina(Long id) {
        Squadra squadra = this.squadraRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Squadra non trovata con id " + id));

        boolean haPartite = !squadra.getPartiteInCasa().isEmpty()
                          || !squadra.getPartiteInTrasferta().isEmpty();

        if (haPartite) {
            throw new IllegalStateException(
                "Impossibile eliminare la squadra: esistono partite (giocate o programmate) "
                + "che la coinvolgono. Elimina prima quelle partite."
            );
        }

        for (Torneo torneo : new HashSet<>(squadra.getTornei())) {
            torneo.getSquadre().remove(squadra);
        }
        squadra.getTornei().clear();

        this.squadraRepository.delete(squadra);
    }
}