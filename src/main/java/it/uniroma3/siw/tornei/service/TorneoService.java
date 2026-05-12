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
		this.torneoRepository=torneoRepository;
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
}
