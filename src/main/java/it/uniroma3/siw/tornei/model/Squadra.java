package it.uniroma3.siw.tornei.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Squadra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Il nome della squadra e' obbligatorio")
	@Size(max = 255, message = "Il nome non puo' superare 255 caratteri")
	@Column(nullable = false)
	private String nome;

	@NotBlank(message = "La citta' e' obbligatoria")
	@Size(max = 255, message = "La citta' non puo' superare 255 caratteri")
	@Column(nullable = false)
	private String citta;

	@NotNull(message = "L'anno di fondazione e' obbligatorio")
	@Min(value = 1800, message = "L'anno di fondazione deve essere successivo al 1800")
	@Max(value = 2100, message = "L'anno di fondazione non puo' superare il 2100")
	@Column(nullable = false)
	private Integer annoFondazione;

	@ManyToMany(mappedBy = "squadre")
	private Set<Torneo> tornei = new HashSet<>();

	@OneToMany(mappedBy = "squadra", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Giocatore> giocatori = new ArrayList<>();

	@OneToMany(mappedBy = "squadraHome")
	private List<Partita> partiteInCasa = new ArrayList<>();

	@OneToMany(mappedBy = "squadraAway")
	private List<Partita> partiteInTrasferta = new ArrayList<>();

	public Squadra() {
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

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public Integer getAnnoFondazione() {
		return annoFondazione;
	}

	public void setAnnoFondazione(Integer annoFondazione) {
		this.annoFondazione = annoFondazione;
	}

	public List<Giocatore> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(List<Giocatore> giocatori) {
		this.giocatori = giocatori;
	}

	public List<Partita> getPartiteInCasa() {
		return partiteInCasa;
	}

	public void setPartiteInCasa(List<Partita> partiteInCasa) {
		this.partiteInCasa = partiteInCasa;
	}

	public List<Partita> getPartiteInTrasferta() {
		return partiteInTrasferta;
	}

	public void setPartiteInTrasferta(List<Partita> partiteInTrasferta) {
		this.partiteInTrasferta = partiteInTrasferta;
	}

	public Set<Torneo> getTornei() {
		return tornei;
	}

	public void setTornei(Set<Torneo> tornei) {
		this.tornei = tornei;
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
		Squadra other = (Squadra) obj;
		return Objects.equals(id, other.id);
	}
}