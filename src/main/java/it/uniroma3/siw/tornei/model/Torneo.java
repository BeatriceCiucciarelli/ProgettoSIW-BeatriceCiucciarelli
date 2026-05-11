package it.uniroma3.siw.tornei.model;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Torneo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private Integer anno;
	
	@Column(length=2000)
	private String descrizione;
	
	
	//COSTRUTTORE
	public Torneo() {
		super();
	}
	
	@ManyToMany
	private Set<Squadra> squadre;

	@OneToMany(mappedBy="torneo")
	private List<Partita> partite;


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


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Torneo other = (Torneo) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
