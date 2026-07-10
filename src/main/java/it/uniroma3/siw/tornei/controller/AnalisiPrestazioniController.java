package it.uniroma3.siw.tornei.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.tornei.dto.RisultatoAnalisi;
import it.uniroma3.siw.tornei.service.AnalisiPrestazioniService;
import it.uniroma3.siw.tornei.service.TorneoService;

@Controller
public class AnalisiPrestazioniController {

    private final AnalisiPrestazioniService analisiPrestazioniService;
    private final TorneoService torneoService;

    public AnalisiPrestazioniController(AnalisiPrestazioniService analisiPrestazioniService,
                                         TorneoService torneoService) {
        this.analisiPrestazioniService = analisiPrestazioniService;
        this.torneoService = torneoService;
    }

    @GetMapping("/admin/analisi-prestazioni")
    public String analisi(@RequestParam(value = "torneoId", required = false) Long torneoId, Model model) {
        model.addAttribute("tornei", this.torneoService.findAll());

        if (torneoId != null) {
            List<RisultatoAnalisi> risultati = this.analisiPrestazioniService.confrontaStrategie(torneoId);
            model.addAttribute("risultati", risultati);
            model.addAttribute("torneoSelezionato", torneoId);
        }

        return "admin/analisi-prestazioni";
    }
}