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

    /*
     * Validazione manuale dei goal: qui NON passiamo per @Valid (il metodo
     * riceve due Integer via @RequestParam, non un oggetto Partita
     * completo), quindi il controllo va fatto esplicitamente.
     */
    @Transactional
    public Partita inserisciRisultato(Long id, Integer goalsHome, Integer goalsAway) {
        if (goalsHome == null || goalsAway == null) {
            throw new IllegalArgumentException("Il risultato deve avere entrambi i punteggi");
        }
        if (goalsHome < 0 || goalsAway < 0) {
            throw new IllegalArgumentException("I goal non possono essere negativi");
        }

        Partita partita = this.partitaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Partita non trovata con id " + id));

        partita.setGoalsHome(goalsHome);
        partita.setGoalsAway(goalsAway);
        partita.setStato(Partita.StatoPartita.PLAYED);

        return partita;
    }

    @Transactional
    public void elimina(Long id) {
        Partita partita = this.partitaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Partita non trovata con id " + id));

        this.partitaRepository.delete(partita);
    }
}