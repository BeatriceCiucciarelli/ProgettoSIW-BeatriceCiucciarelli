package it.uniroma3.siw.tornei.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
public class Giocatore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Il nome e' obbligatorio")
	@Size(max = 255, message = "Il nome non puo' superare 255 caratteri")
	@Column(nullable = false)
	private String nome;

	@NotBlank(message = "Il cognome e' obbligatorio")
	@Size(max = 255, message = "Il cognome non puo' superare 255 caratteri")
	@Column(nullable = false)
	private String cognome;

	@NotNull(message = "La data di nascita e' obbligatoria")
	@Past(message = "La data di nascita deve essere nel passato")
	@Column(nullable = false)
	private LocalDate dataDiNascita;

	@NotNull(message = "L'altezza e' obbligatoria")
	@Min(value = 100, message = "L'altezza deve essere almeno 100 cm")
	@Max(value = 250, message = "L'altezza non puo' superare 250 cm")
	@Column(nullable = false)
	private Integer altezza;

	@NotNull(message = "Il ruolo e' obbligatorio")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Ruolo ruolo;

	public enum Ruolo {
		PORTIERE,
		DIFENSORE,
		CENTROCAMPISTA,
		ATTACCANTE
	}

	@ManyToOne
	@JoinColumn(name = "squadra_id", nullable = false)
	private Squadra squadra;

	public Giocatore() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(LocalDate dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public Integer getAltezza() {
		return altezza;
	}

	public void setAltezza(Integer altezza) {
		this.altezza = altezza;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	public Squadra getSquadra() {
		return squadra;
	}

	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
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
		Giocatore other = (Giocatore) obj;
		return Objects.equals(id, other.id);
	}
}