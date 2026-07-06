package it.uniroma3.siw.tornei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /*
     * Caso d'uso: creazione di un torneo (Sezione 4.3 - Amministratore).
     * Operazione di scrittura -> @Transactional (non readOnly).
     */
    @Transactional
    public Torneo salva(Torneo torneo) {
        return this.torneoRepository.save(torneo);
    }

    /*
     * Caso d'uso: modifica di un torneo (Sezione 4.3 - Amministratore).
     * Si aggiornano solo i campi scalari (nome, anno, descrizione):
     * NON si toccano le collezioni (squadre, partite) perche' il form
     * di modifica non le gestisce, ed e' importante non sovrascriverle
     * per errore con valori vuoti provenienti dal form.
     */
    @Transactional
    public Torneo aggiorna(Long id, Torneo datiAggiornati) {
        Torneo torneo = this.torneoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Torneo non trovato con id " + id));

        torneo.setNome(datiAggiornati.getNome());
        torneo.setAnno(datiAggiornati.getAnno());
        torneo.setDescrizione(datiAggiornati.getDescrizione());

        // nessun bisogno di richiamare save(): l'entita' e' gestita (dirty
        // checking) dentro la transazione, Hibernate fa l'UPDATE da solo
        return torneo;
    }
}