package it.uniroma3.siw.tornei.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.tornei.model.Torneo;

public interface TorneoRepository extends JpaRepository<Torneo, Long>{

}