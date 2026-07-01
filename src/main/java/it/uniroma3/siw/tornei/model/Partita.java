package it.uniroma3.siw.tornei.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Partita {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime dataOra;

	@Column(nullable = false)
	private String luogo;

	private Integer goalsHome;

	private Integer goalsAway;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatoPartita stato;

	@ManyToOne
	@JoinColumn(name = "torneo_id", nullable = false)
	private Torneo torneo;

	@ManyToOne
	@JoinColumn(name = "squadra_home_id", nullable = false)
	private Squadra squadraHome;

	@ManyToOne
	@JoinColumn(name = "squadra_away_id", nullable = false)
	private Squadra squadraAway;

	@ManyToOne
	@JoinColumn(name = "arbitro_id", nullable = false)
	private Arbitro arbitro;

	@OneToMany(mappedBy = "partita", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Commento> commenti = new ArrayList<>();

	public enum StatoPartita {
		SCHEDULED,
		PLAYED,
		CANCELED;
	}

	// COSTRUTTORE
	public Partita() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataOra() {
		return dataOra;
	}

	public void setDataOra(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public Integer getGoalsHome() {
		return goalsHome;
	}

	public void setGoalsHome(Integer goalsHome) {
		this.goalsHome = goalsHome;
	}

	public Integer getGoalsAway() {
		return goalsAway;
	}

	public void setGoalsAway(Integer goalsAway) {
		this.goalsAway = goalsAway;
	}

	public StatoPartita getStato() {
		return stato;
	}

	public void setStato(StatoPartita stato) {
		this.stato = stato;
	}

	public Torneo getTorneo() {
		return torneo;
	}

	public void setTorneo(Torneo torneo) {
		this.torneo = torneo;
	}

	public Squadra getSquadraHome() {
		return squadraHome;
	}

	public void setSquadraHome(Squadra squadraHome) {
		this.squadraHome = squadraHome;
	}

	public Squadra getSquadraAway() {
		return squadraAway;
	}

	public void setSquadraAway(Squadra squadraAway) {
		this.squadraAway = squadraAway;
	}

	public Arbitro getArbitro() {
		return arbitro;
	}

	public void setArbitro(Arbitro arbitro) {
		this.arbitro = arbitro;
	}

	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Partita other = (Partita) obj;
		return Objects.equals(id, other.id);
	}
}