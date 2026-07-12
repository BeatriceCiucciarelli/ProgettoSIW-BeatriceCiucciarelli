package it.uniroma3.siw.tornei.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.tornei.model.Giocatore;

public interface GiocatoreRepository extends JpaRepository<Giocatore, Long> {

}