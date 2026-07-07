package it.uniroma3.siw.tornei.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.tornei.model.Giocatore;
import it.uniroma3.siw.tornei.model.Squadra;
import it.uniroma3.siw.tornei.service.GiocatoreService;
import it.uniroma3.siw.tornei.service.SquadraService;

@Controller
public class GiocatoreController {

    private final GiocatoreService giocatoreService;
    private final SquadraService squadraService;

    public GiocatoreController(GiocatoreService giocatoreService, SquadraService squadraService) {
        this.giocatoreService = giocatoreService;
        this.squadraService = squadraService;
    }

    // Il giocatore viene sempre creato "dentro" una squadra specifica
    @GetMapping("/admin/squadre/{squadraId}/giocatori/nuovo")
    public String formNuovoGiocatore(@PathVariable("squadraId") Long squadraId, Model model) {
        Optional<Squadra> squadraOptional = this.squadraService.findById(squadraId);
        if (squadraOptional.isEmpty()) {
            return "redirect:/squadre";
        }
        Giocatore giocatore = new Giocatore();
        giocatore.setSquadra(squadraOptional.get());
        model.addAttribute("giocatore", giocatore);
        return "admin/giocatori/form";
    }

    @PostMapping("/admin/squadre/{squadraId}/giocatori")
    public String creaGiocatore(@PathVariable("squadraId") Long squadraId,
                                 @ModelAttribute("giocatore") Giocatore giocatore) {

        Squadra squadra = this.squadraService.findById(squadraId)
            .orElseThrow(() -> new IllegalArgumentException("Squadra non trovata"));

        giocatore.setSquadra(squadra);
        this.giocatoreService.salva(giocatore);

        return "redirect:/squadre/" + squadraId;
    }

    @GetMapping("/admin/giocatori/{id}/modifica")
    public String formModificaGiocatore(@PathVariable("id") Long id, Model model) {
        Optional<Giocatore> giocatoreOptional = this.giocatoreService.findById(id);
        if (giocatoreOptional.isEmpty()) {
            return "redirect:/squadre";
        }
        model.addAttribute("giocatore", giocatoreOptional.get());
        return "admin/giocatori/form";
    }

    @PostMapping("/admin/giocatori/{id}")
    public String aggiornaGiocatore(@PathVariable("id") Long id,
                                     @ModelAttribute("giocatore") Giocatore giocatoreForm) {

        Giocatore aggiornato = this.giocatoreService.aggiorna(id, giocatoreForm);
        return "redirect:/squadre/" + aggiornato.getSquadra().getId();
    }
}