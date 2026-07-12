package it.uniroma3.siw.tornei.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Commento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 1000)
	@Column(nullable = false, length = 1000)
	private String testo;

	@Column(nullable = false)
	private LocalDateTime dataCreazione;

	@Column(nullable = false)
	private LocalDateTime dataUltimaModifica;

	@ManyToOne
	@JoinColumn(name = "utente_id", nullable = false)
	private Utente autore;

	@ManyToOne
	@JoinColumn(name = "partita_id", nullable = false)
	private Partita partita;

	public Commento() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public LocalDateTime getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	public void setDataUltimaModifica(LocalDateTime dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public Utente getAutore() {
		return autore;
	}

	public void setAutore(Utente autore) {
		this.autore = autore;
	}

	public Partita getPartita() {
		return partita;
	}

	public void setPartita(Partita partita) {
		this.partita = partita;
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
		Commento other = (Commento) obj;
		return Objects.equals(id, other.id);
	}
}