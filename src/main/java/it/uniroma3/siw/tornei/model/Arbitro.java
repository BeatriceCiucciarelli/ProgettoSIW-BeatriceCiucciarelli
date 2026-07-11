package it.uniroma3.siw.tornei.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Arbitro {

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

	@NotBlank(message = "Il codice arbitrale e' obbligatorio")
	@Size(max = 50, message = "Il codice arbitrale non puo' superare 50 caratteri")
	@Column(nullable = false, unique = true)
	private String codiceArbitrale;

	@OneToMany(mappedBy = "arbitro")
	private List<Partita> partite = new ArrayList<>();

	public Arbitro() {
	}

	public List<Partita> getPartite() {
		return partite;
	}

	public void setPartite(List<Partita> partite) {
		this.partite = partite;
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

	public String getCodiceArbitrale() {
		return codiceArbitrale;
	}

	public void setCodiceArbitrale(String codiceArbitrale) {
		this.codiceArbitrale = codiceArbitrale;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codiceArbitrale);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Arbitro other = (Arbitro) obj;
		return Objects.equals(codiceArbitrale, other.codiceArbitrale);
	}
}