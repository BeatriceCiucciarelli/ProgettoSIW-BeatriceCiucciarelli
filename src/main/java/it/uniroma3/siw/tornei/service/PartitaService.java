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

    /* Caso d'uso: registrazione di una partita (Sezione 4.3 - Amministratore).
     * La partita viene creata con stato SCHEDULED e senza risultato:
     * l'inserimento del risultato e' un caso d'uso separato (prossimo passo). */
    @Transactional
    public Partita salva(Partita partita) {
        return this.partitaRepository.save(partita);
    }
}