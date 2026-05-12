package it.uniroma3.siw.tornei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.tornei.model.Giocatore;
import it.uniroma3.siw.tornei.repository.GiocatoreRopository;

@Service
public class GiocatoreService {

    private final GiocatoreRopository giocatoreRepository;

    public GiocatoreService(GiocatoreRopository giocatoreRepository) {
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
}
