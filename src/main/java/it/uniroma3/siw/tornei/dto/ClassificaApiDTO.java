package it.uniroma3.siw.tornei.dto;

/*
 * DTO "piatto" per l'endpoint REST della classifica, usato dal componente
 * React (Sezione 9). Contiene solo tipi primitivi/String, NESSUN riferimento
 * a entita' JPA: cosi' Jackson lo serializza in JSON senza rischiare
 * LazyInitializationException o riferimenti circolari (es. Squadra <-> Torneo).
 */
public class ClassificaApiDTO {

    private Long squadraId;
    private String squadraNome;
    private int punti;
    private int partiteGiocate;
    private int vittorie;
    private int pareggi;
    private int sconfitte;
    private int golFatti;
    private int golSubiti;
    private int differenzaReti;

    public Long getSquadraId() {
        return squadraId;
    }

    public void setSquadraId(Long squadraId) {
        this.squadraId = squadraId;
    }

    public String getSquadraNome() {
        return squadraNome;
    }

    public void setSquadraNome(String squadraNome) {
        this.squadraNome = squadraNome;
    }

    public int getPunti() {
        return punti;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    public int getPartiteGiocate() {
        return partiteGiocate;
    }

    public void setPartiteGiocate(int partiteGiocate) {
        this.partiteGiocate = partiteGiocate;
    }

    public int getVittorie() {
        return vittorie;
    }

    public void setVittorie(int vittorie) {
        this.vittorie = vittorie;
    }

    public int getPareggi() {
        return pareggi;
    }

    public void setPareggi(int pareggi) {
        this.pareggi = pareggi;
    }

    public int getSconfitte() {
        return sconfitte;
    }

    public void setSconfitte(int sconfitte) {
        this.sconfitte = sconfitte;
    }

    public int getGolFatti() {
        return golFatti;
    }

    public void setGolFatti(int golFatti) {
        this.golFatti = golFatti;
    }

    public int getGolSubiti() {
        return golSubiti;
    }

    public void setGolSubiti(int golSubiti) {
        this.golSubiti = golSubiti;
    }

    public int getDifferenzaReti() {
        return differenzaReti;
    }

    public void setDifferenzaReti(int differenzaReti) {
        this.differenzaReti = differenzaReti;
    }
}