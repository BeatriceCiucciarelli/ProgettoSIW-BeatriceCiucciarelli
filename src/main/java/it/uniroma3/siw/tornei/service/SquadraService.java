package it.uniroma3.siw.tornei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.tornei.model.Squadra;
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
}
