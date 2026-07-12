package it.uniroma3.siw.tornei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.tornei.model.Giocatore;
import it.uniroma3.siw.tornei.repository.GiocatoreRepository;

@Service
public class GiocatoreService {

    private final GiocatoreRepository giocatoreRepository;

    public GiocatoreService(GiocatoreRepository giocatoreRepository) {
        this.giocatoreRepository = giocatoreRepository;
    }

    @Transactional(readOnly = true)
    public List<Giocatore> findAll() {
        List<Giocatore> giocatori = new ArrayList<>();
        this.giocatoreRepository.findAll().forEach(giocatori::add);
        return giocatori;
    }

    @Transactional(readOnly = true)
    public Optional<Giocatore> findById(Long id) {
        return this.giocatoreRepository.findById(id);
    }

    /* Caso d'uso: inserimento giocatore (Sezione 4.3 - Amministratore).
     * La squadra viene assegnata dal controller PRIMA di chiamare questo
     * metodo (il form non la gestisce direttamente). */
    @Transactional
    public Giocatore salva(Giocatore giocatore) {
        return this.giocatoreRepository.save(giocatore);
    }

    /* Caso d'uso: modifica giocatore (Sezione 4.3 - Amministratore).
     * Aggiorna solo i campi scalari, NON la squadra di appartenenza. */
    @Transactional
    public Giocatore aggiorna(Long id, Giocatore datiAggiornati) {
        Giocatore giocatore = this.giocatoreRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Giocatore non trovato con id " + id));

        giocatore.setNome(datiAggiornati.getNome());
        giocatore.setCognome(datiAggiornati.getCognome());
        giocatore.setDataDiNascita(datiAggiornati.getDataDiNascita());
        giocatore.setAltezza(datiAggiornati.getAltezza());
        giocatore.setRuolo(datiAggiornati.getRuolo());

        return giocatore;
    }
}