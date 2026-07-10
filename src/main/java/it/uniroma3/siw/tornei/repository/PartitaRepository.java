package it.uniroma3.siw.tornei.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.tornei.model.Partita;

public interface PartitaRepository extends JpaRepository<Partita, Long> {

    /*
     * STRATEGIA 1 (baseline): query derivata "naturale".
     * Le associazioni squadraHome/squadraAway sono @ManyToOne, quindi EAGER
     * per default in JPA. Ma "EAGER" NON vuol dire "in un'unica query":
     * il fetch mode di default di Hibernate per le associazioni EAGER e'
     * SELECT, cioe' una query aggiuntiva PER OGNI riga del risultato ->
     * problema classico N+1 (1 query per le partite + N query, una per
     * ogni squadra home/away coinvolta).
     */
    List<Partita> findByTorneoId(Long torneoId);

    /*
     * STRATEGIA 2: JOIN FETCH esplicito in JPQL.
     * Un'unica query SQL con i JOIN carica partite + squadre in un colpo solo.
     */
    @Query("select p from Partita p "
         + "join fetch p.squadraHome "
         + "join fetch p.squadraAway "
         + "where p.torneo.id = :torneoId")
    List<Partita> findByTorneoIdJoinFetch(@Param("torneoId") Long torneoId);

    /*
     * STRATEGIA 3: @EntityGraph.
     * Stesso risultato (query unica con JOIN) della strategia 2, ma
     * dichiarato in modo dichiarativo/annotato invece che scrivendo JPQL
     * a mano. Il nome del metodo e' diverso da findByTorneoId solo per
     * evitare un conflitto di firma: e' comunque una query derivata
     * equivalente ("findAllBy" e "findBy" sono sinonimi in Spring Data).
     */
    @EntityGraph(attributePaths = {"squadraHome", "squadraAway"})
    List<Partita> findAllByTorneoId(Long torneoId);
}