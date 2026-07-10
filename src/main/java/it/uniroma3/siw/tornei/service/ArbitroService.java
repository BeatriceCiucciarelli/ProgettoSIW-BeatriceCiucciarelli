package it.uniroma3.siw.tornei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.tornei.model.Arbitro;
import it.uniroma3.siw.tornei.repository.ArbitroRepository;

@Service
public class ArbitroService {

    private final ArbitroRepository arbitroRepository;

    public ArbitroService(ArbitroRepository arbitroRepository) {
        this.arbitroRepository = arbitroRepository;
    }

    @Transactional(readOnly = true)
    public List<Arbitro> findAll() {
        List<Arbitro> arbitri = new ArrayList<>();
        this.arbitroRepository.findAll().forEach(arbitri::add);
        return arbitri;
    }

    @Transactional(readOnly = true)
    public Optional<Arbitro> findById(Long id) {
        return this.arbitroRepository.findById(id);
    }
}