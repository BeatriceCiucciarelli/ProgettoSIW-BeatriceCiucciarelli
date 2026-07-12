package it.uniroma3.siw.tornei.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.tornei.exception.UsernameGiaRegistratoException;
import it.uniroma3.siw.tornei.model.Utente;
import it.uniroma3.siw.tornei.repository.UtenteRepository;

@Service
public class UtenteService {

	private UtenteRepository utenteRepository;
	private PasswordEncoder passwordEncoder;

	public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
		this.utenteRepository = utenteRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public Utente registraNuovoUtente(Utente utente) {
		if (this.utenteRepository.findByUsername(utente.getUsername()).isPresent()) {
			throw new UsernameGiaRegistratoException();
		}

		utente.setPassword(this.passwordEncoder.encode(utente.getPassword()));
		utente.setRuolo("USER");

		return this.utenteRepository.save(utente);
	}
}