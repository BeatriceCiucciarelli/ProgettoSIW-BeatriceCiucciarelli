package it.uniroma3.siw.tornei.controller;

import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.tornei.model.Commento;
import it.uniroma3.siw.tornei.model.Partita;
import it.uniroma3.siw.tornei.model.Utente;
import it.uniroma3.siw.tornei.repository.UtenteRepository;
import it.uniroma3.siw.tornei.service.CommentoService;
import it.uniroma3.siw.tornei.service.PartitaService;

@Controller
public class CommentoController {

    private final CommentoService commentoService;
    private final PartitaService partitaService;
    private final UtenteRepository utenteRepository;

    public CommentoController(CommentoService commentoService,
                               PartitaService partitaService,
                               UtenteRepository utenteRepository) {
        this.commentoService = commentoService;
        this.partitaService = partitaService;
        this.utenteRepository = utenteRepository;
    }

    @PostMapping("/partite/{partitaId}/commenti")
    public String creaCommento(@PathVariable("partitaId") Long partitaId,
                                @RequestParam("testo") String testo,
                                Authentication authentication) {

        Utente autore = utenteRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new IllegalStateException("Utente autenticato non trovato nel DB"));

        Partita partita = partitaService.findById(partitaId)
            .orElseThrow(() -> new IllegalArgumentException("Partita non trovata"));

        commentoService.creaCommento(testo, autore, partita);

        return "redirect:/partite/" + partitaId;
    }

    @GetMapping("/commenti/{id}/modifica")
    public String formModifica(@PathVariable("id") Long id, Model model, Authentication authentication) {
        Optional<Commento> commentoOptional = commentoService.findById(id);

        if (commentoOptional.isEmpty()) {
            return "redirect:/";
        }

        Commento commento = commentoOptional.get();

        //se il commento non è tuo:
        if (!commento.getAutore().getUsername().equals(authentication.getName())) {
            throw new AccessDeniedException("Non puoi modificare un commento che non e' tuo");
        }

        model.addAttribute("commento", commento);
        return "commenti/modifica";
    }

    @PostMapping("/commenti/{id}")
    public String aggiornaCommento(@PathVariable("id") Long id,
                                    @RequestParam("testo") String testo,
                                    Authentication authentication) {

        Commento commento = commentoService.aggiornaCommento(id, testo, authentication.getName());

        return "redirect:/partite/" + commento.getPartita().getId();
    }
}