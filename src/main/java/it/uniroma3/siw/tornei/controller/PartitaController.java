package it.uniroma3.siw.tornei.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    // ===================== FUNZIONALITA' PUBBLICHE (Sezione 4.1) =====================

    @GetMapping("/partite")
    public String list(Model model) {
        List<Partita> partite = this.partitaService.findAll();
        model.addAttribute("partite", partite);
        return "partite/list";
    }

    @GetMapping("/partite/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<Partita> partitaOptional = this.partitaService.findById(id);
        if (partitaOptional.isPresent()) {
            model.addAttribute("partita", partitaOptional.get());
        }
        return "partite/show";
    }

    // ===================== FUNZIONALITA' ADMIN (Sezione 4.3) =====================

    @GetMapping("/admin/partite/nuova")
    public String formNuovaPartita(Model model) {
        model.addAttribute("partita", new Partita());
        model.addAttribute("tornei", this.torneoService.findAll());
        model.addAttribute("squadre", this.squadraService.findAll());
        model.addAttribute("arbitri", this.arbitroService.findAll());
        return "admin/partite/form";
    }

    @PostMapping("/admin/partite")
    public String creaPartita(@ModelAttribute("partita") Partita partita,
                               @RequestParam("torneoId") Long torneoId,
                               @RequestParam("squadraHomeId") Long squadraHomeId,
                               @RequestParam("squadraAwayId") Long squadraAwayId,
                               @RequestParam("arbitroId") Long arbitroId) {

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
        // una partita appena registrata e' sempre programmata, senza risultato
        partita.setStato(Partita.StatoPartita.SCHEDULED);

        Partita salvata = this.partitaService.salva(partita);

        return "redirect:/partite/" + salvata.getId();
    }
}