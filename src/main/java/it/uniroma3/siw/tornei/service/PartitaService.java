package it.uniroma3.siw.tornei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.tornei.model.Partita;
import it.uniroma3.siw.tornei.repository.PartitaRepository;

@Service
public class PartitaService {

    private final PartitaRepository partitaRepository;

    public PartitaService(PartitaRepository partitaRepository) {
        this.partitaRepository = partitaRepository;
    }

    @Transactional(readOnly = true)
    public List<Partita> findAll() {
        List<Partita> partite = new ArrayList<>();
        this.partitaRepository.findAll().forEach(partite::add);
        return partite;
    }

    @Transactional(readOnly = true)
    public Optional<Partita> findById(Long id) {
        return this.partitaRepository.findById(id);
    }

    @Transactional
    public Partita salva(Partita partita) {
        return this.partitaRepository.save(partita);
    }

    @Transactional
    public Partita inserisciRisultato(Long id, Integer goalsHome, Integer goalsAway) {
        Partita partita = this.partitaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Partita non trovata con id " + id));

        partita.setGoalsHome(goalsHome);
        partita.setGoalsAway(goalsAway);
        partita.setStato(Partita.StatoPartita.PLAYED);

        return partita;
    }

    /*
     * Caso d'uso: eliminazione di una partita (Sezione 4.3 - Amministratore).
     * Nessun controllo di integrita' necessario qui: i commenti collegati
     * si cancellano automaticamente grazie a cascade=ALL + orphanRemoval=true
     * su Partita.commenti. Nessun'altra entita' dipende da Partita.
     */
    @Transactional
    public void elimina(Long id) {
        Partita partita = this.partitaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Partita non trovata con id " + id));

        this.partitaRepository.delete(partita);
    }
}