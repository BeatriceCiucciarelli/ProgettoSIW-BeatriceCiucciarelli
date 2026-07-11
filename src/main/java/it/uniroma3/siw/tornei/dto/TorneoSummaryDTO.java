package it.uniroma3.siw.tornei.dto;

/*
 * DTO piatto per l'endpoint REST che elenca i tornei (usato dalla select
 * del componente React). Come ClassificaApiDTO, niente riferimenti a
 * entita' JPA per evitare problemi di serializzazione JSON.
 */
public class TorneoSummaryDTO {

    private Long id;
    private String nome;
    private Integer anno;

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
}