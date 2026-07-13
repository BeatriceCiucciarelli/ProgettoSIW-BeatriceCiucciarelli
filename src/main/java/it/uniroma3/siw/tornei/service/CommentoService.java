package it.uniroma3.siw.tornei.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.tornei.model.Commento;
import it.uniroma3.siw.tornei.model.Partita;
import it.uniroma3.siw.tornei.model.Utente;
import it.uniroma3.siw.tornei.repository.CommentoRepository;

@Service
public class CommentoService {

    private final CommentoRepository commentoRepository;

    public CommentoService(CommentoRepository commentoRepository) {
        this.commentoRepository = commentoRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Commento> findById(Long id) {
        return commentoRepository.findById(id);		//restituisce lo specifico commento dentro l?optional
    }

    /*
     * Caso d'uso: inserimento di un commento (4.2).
     * Operazione di scrittura
     */
    @Transactional
    public Commento creaCommento(String testo, Utente autore, Partita partita) {
        Commento commento = new Commento();
        commento.setTesto(testo);
        commento.setAutore(autore);
        commento.setPartita(partita);
        LocalDateTime adesso = LocalDateTime.now();
        commento.setDataCreazione(adesso);
        commento.setDataUltimaModifica(adesso);
        return commentoRepository.save(commento);
    }

    /*
     * Caso d'uso: modifica di un proprio commento (Sezione 4.2).
     * controllo "solo i propri commenti" va fatto qui
     */
    @Transactional
    public Commento aggiornaCommento(Long commentoId, String nuovoTesto, String usernameRichiedente) {
        Commento commento = commentoRepository.findById(commentoId)
            .orElseThrow(() -> new IllegalArgumentException("Commento non trovato"));

        //se il commento non è tuo:
        if (!commento.getAutore().getUsername().equals(usernameRichiedente)) {
            throw new AccessDeniedException("Non puoi modificare un commento che non e' tuo");
        }

        commento.setTesto(nuovoTesto);
        commento.setDataUltimaModifica(LocalDateTime.now());
        // nessun bisogno di richiamare save(): l'entita' e' gestita (dirty checking)
        // dentro la transazione, Hibernate fa l'UPDATE da solo al commit
        return commento;
    }
}