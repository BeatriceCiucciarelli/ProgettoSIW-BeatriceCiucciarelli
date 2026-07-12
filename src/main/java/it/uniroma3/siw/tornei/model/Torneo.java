package it.uniroma3.siw.tornei.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Torneo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 255)
	
	@Column(nullable = false)
	private String nome;
	
	@NotNull
	@Min(value = 1900)
	@Max(value = 2100)
	
	@Column(nullable = false)
	private Integer anno;
	
	@Size(max = 2000)
	
	@Column(length = 2000)
	private String descrizione;
	
	@ManyToMany
	@JoinTable(
		name = "torneo_squadra",
		joinColumns = @JoinColumn(name = "torneo_id"),
		inverseJoinColumns = @JoinColumn(name = "squadra_id")
	)
	private Set<Squadra> squadre = new HashSet<>();

	@OneToMany(mappedBy = "torneo")
	private List<Partita> partite = new ArrayList<>();

	// COSTRUTTORE
	public Torneo() {
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

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set<Squadra> getSquadre() {
		return squadre;
	}

	public void setSquadre(Set<Squadra> squadre) {
		this.squadre = squadre;
	}

	public List<Partita> getPartite() {
		return partite;
	}

	public void setPartite(List<Partita> partite) {
		this.partite = partite;
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
		Torneo other = (Torneo) obj;
		return Objects.equals(id, other.id);
	}
}