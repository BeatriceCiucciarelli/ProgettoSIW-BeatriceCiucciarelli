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

@Entity
public class Arbitro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String cognome;
	 
	@Column(nullable = false, unique = true)
	private String codiceArbitrale;
	 
	@OneToMany(mappedBy = "arbitro")
	private List<Partita> partite = new ArrayList<>();
	 
	// COSTRUTTORE
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