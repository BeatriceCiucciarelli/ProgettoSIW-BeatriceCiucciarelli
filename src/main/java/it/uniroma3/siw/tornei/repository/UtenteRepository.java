package it.uniroma3.siw.tornei.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.tornei.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long>{
	Optional<Utente> findByUsername(String username);
}