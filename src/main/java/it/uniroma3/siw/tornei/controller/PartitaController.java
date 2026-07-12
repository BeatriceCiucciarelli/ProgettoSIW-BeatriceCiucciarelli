package it.uniroma3.siw.tornei.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.tornei.model.Arbitro;
import it.uniroma3.siw.tornei.model.Partita;
import it.uniroma3.siw.tornei.model.Squadra;
import it.uniroma3.siw.tornei.model.Torneo;
import it.uniroma3.siw.tornei.service.ArbitroService;
import it.uniroma3.siw.tornei.service.PartitaService;
import it.uniroma3.siw.tornei.service.SquadraService;
import it.uniroma3.siw.tornei.service.TorneoService;
import jakarta.validation.Valid;

@Controller
public class PartitaController {

    private final PartitaService partitaService;
    private final TorneoService torneoService;
    private final SquadraService squadraService;
    private final ArbitroService arbitroService;

    public PartitaController(PartitaService partitaService,
                              TorneoService torneoService,
                              SquadraService squadraService,
                              ArbitroService arbitroService) {
        this.partitaService = partitaService;
        this.torneoService = torneoService;
        this.squadraService = squadraService;
        this.arbitroService = arbitroService;
    }

    @GetMapping("/partite")
    public String list(Model model) {
        List<Partita> partite = this.partitaService.findAll();
        model.addAttribute("partite", partite);
        return "partite/list";
    }

    @GetMapping("/partite/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<Partita> partitaOptional = this.partitaService.findById(id);
        if (partitaOptional.isEmpty()) {
            return "redirect:/partite";
        }
        model.addAttribute("partita", partitaOptional.get());
        return "partite/show";
    }

    @GetMapping("/admin/partite/nuova")
    public String formNuovaPartita(Model model) {
        model.addAttribute("partita", new Partita());
        model.addAttribute("tornei", this.torneoService.findAll());
        model.addAttribute("squadre", this.squadraService.findAll());
        model.addAttribute("arbitri", this.arbitroService.findAll());
        return "admin/partite/form";
    }

    @PostMapping("/admin/partite")
    public String creaPartita(@Valid @ModelAttribute("partita") Partita partita,
                               BindingResult bindingResult,
                               @RequestParam("torneoId") Long torneoId,
                               @RequestParam("squadraHomeId") Long squadraHomeId,
                               @RequestParam("squadraAwayId") Long squadraAwayId,
                               @RequestParam("arbitroId") Long arbitroId,
                               Model model) {

        // @Valid ha gia' controllato dataOra/luogo/goals. Se ci sono errori,
        // dobbiamo ripopolare le select prima di ri-mostrare il form,
        // altrimenti sarebbero vuote.
        if (bindingResult.hasErrors()) {
            model.addAttribute("tornei", this.torneoService.findAll());
            model.addAttribute("squadre", this.squadraService.findAll());
            model.addAttribute("arbitri", this.arbitroService.findAll());
            return "admin/partite/form";
        }

        Torneo torneo = this.torneoService.findById(torneoId)
            .orElseThrow(() -> new IllegalArgumentException("Torneo non trovato"));
        Squadra squadraHome = this.squadraService.findById(squadraHomeId)
            .orElseThrow(() -> new IllegalArgumentException("Squadra home non trovata"));
        Squadra squadraAway = this.squadraService.findById(squadraAwayId)
            .orElseThrow(() -> new IllegalArgumentException("Squadra away non trovata"));
        Arbitro arbitro = this.arbitroService.findById(arbitroId)
            .orElseThrow(() -> new IllegalArgumentException("Arbitro non trovato"));

        partita.setTorneo(torneo);
        partita.setSquadraHome(squadraHome);
        partita.setSquadraAway(squadraAway);
        partita.setArbitro(arbitro);
        partita.setStato(Partita.StatoPartita.SCHEDULED);

        Partita salvata = this.partitaService.salva(partita);

        return "redirect:/partite/" + salvata.getId();
    }

    @GetMapping("/admin/partite/{id}/risultato")
    public String formRisultato(@PathVariable("id") Long id, Model model) {
        Optional<Partita> partitaOptional = this.partitaService.findById(id);
        if (partitaOptional.isEmpty()) {
            return "redirect:/partite";
        }
        model.addAttribute("partita", partitaOptional.get());
        return "admin/partite/risultato";
    }

    @PostMapping("/admin/partite/{id}/risultato")
    public String salvaRisultato(@PathVariable("id") Long id,
                                  @RequestParam("goalsHome") Integer goalsHome,
                                  @RequestParam("goalsAway") Integer goalsAway) {

        Partita aggiornata = this.partitaService.inserisciRisultato(id, goalsHome, goalsAway);
        return "redirect:/partite/" + aggiornata.getId();
    }

    @PostMapping("/admin/partite/{id}/elimina")
    public String eliminaPartita(@PathVariable("id") Long id) {
        this.partitaService.elimina(id);
        return "redirect:/partite";
    }
}