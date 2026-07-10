package it.uniroma3.siw.tornei.dto;

/*
 * DTO che rappresenta il risultato della misurazione di UNA strategia
 * di fetch, per l'analisi sperimentale richiesta dalla Sezione 8.2.
 * Non e' un'entita' JPA.
 */
public class RisultatoAnalisi {

    private String strategia;
    private int numeroPartite;
    private long numeroQuery;
    private double tempoMillisecondi;

    public String getStrategia() {
        return strategia;
    }

    public void setStrategia(String strategia) {
        this.strategia = strategia;
    }

    public int getNumeroPartite() {
        return numeroPartite;
    }

    public void setNumeroPartite(int numeroPartite) {
        this.numeroPartite = numeroPartite;
    }

    public long getNumeroQuery() {
        return numeroQuery;
    }

    public void setNumeroQuery(long numeroQuery) {
        this.numeroQuery = numeroQuery;
    }

    public double getTempoMillisecondi() {
        return tempoMillisecondi;
    }

    public void setTempoMillisecondi(double tempoMillisecondi) {
        this.tempoMillisecondi = tempoMillisecondi;
    }
}