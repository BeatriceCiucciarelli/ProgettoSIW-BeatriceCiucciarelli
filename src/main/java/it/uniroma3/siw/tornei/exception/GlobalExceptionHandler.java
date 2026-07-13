package it.uniroma3.siw.tornei.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
 * Gestore globale delle eccezioni: intercetta le eccezioni piu' comuni
 * lanciate dai service (es. "Torneo non trovato") e le trasforma in una
 * pagina di errore leggibile invece di uno stack trace grezzo o una
 * Whitelabel Error Page.
 *
 * @ControllerAdvice si applica a TUTTI i @Controller dell'applicazione,
 * senza doverlo ripetere in ognuno.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /*
     * Lanciata dai service quando si cerca un'entita' che non esiste
     * (es. torneoService.findById(id).orElseThrow(...)) o quando i dati
     * passati sono incoerenti.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String gestisciEntitaNonTrovata(IllegalArgumentException ex, Model model) {
        model.addAttribute("messaggio", ex.getMessage());
        return "error/404";
    }

    /*
     * Lanciata quando un'operazione e' rifiutata per motivi di business logic
     * (es. eliminare una squadra che ha ancora partite associate). Qui la
     * gestiamo con una pagina dedicata invece di un errore 500 generico.
     */
    @ExceptionHandler(IllegalStateException.class)
    public String gestisciOperazioneNonPermessa(IllegalStateException ex, Model model) {
        model.addAttribute("messaggio", ex.getMessage());
        return "error/operazione-non-permessa";
    }

    /*
     * Lanciata da Spring Security quando un utente autenticato tenta
     * un'azione per cui non ha i permessi (es. modificare un commento
     * altrui). Il 403 "generico" di Spring Security e' gia' gestito a
     * livello di filtro per le richieste protette da SecurityConfig, ma
     * questo copre anche i controlli manuali fatti nei service/controller.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public String gestisciAccessoNegato(AccessDeniedException ex, Model model) {
        model.addAttribute("messaggio", ex.getMessage());
        return "error/403";
    }
}