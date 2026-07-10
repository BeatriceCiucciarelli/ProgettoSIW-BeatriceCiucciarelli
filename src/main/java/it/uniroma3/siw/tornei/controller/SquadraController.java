package it.uniroma3.siw.tornei.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.tornei.model.Squadra;
import it.uniroma3.siw.tornei.service.SquadraService;

@Controller
public class SquadraController {

    private final SquadraService squadraService;

    public SquadraController(SquadraService squadraService) {
        this.squadraService = squadraService;
    }

    // ===================== FUNZIONALITA' PUBBLICHE (Sezione 4.1) =====================

    @GetMapping("/squadre")
    public String list(Model model) {
        List<Squadra> squadre = this.squadraService.findAll();
        model.addAttribute("squadre", squadre);
        return "squadre/list";
    }

    @GetMapping("/squadre/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<Squadra> squadraOptional = this.squadraService.findById(id);
        if (squadraOptional.isPresent()) {
            model.addAttribute("squadra", squadraOptional.get());
        }
        return "squadre/show";
    }

    // ===================== FUNZIONALITA' ADMIN (Sezione 4.3) =====================

    @GetMapping("/admin/squadre/nuova")
    public String formNuovaSquadra(Model model) {
        model.addAttribute("squadra", new Squadra());
        return "admin/squadre/form";
    }

    @PostMapping("/admin/squadre")
    public String creaSquadra(@ModelAttribute("squadra") Squadra squadra) {
        Squadra salvata = this.squadraService.salva(squadra);
        return "redirect:/squadre/" + salvata.getId();
    }

    @GetMapping("/admin/squadre/{id}/modifica")
    public String formModificaSquadra(@PathVariable("id") Long id, Model model) {
        Optional<Squadra> squadraOptional = this.squadraService.findById(id);
        if (squadraOptional.isEmpty()) {
            return "redirect:/squadre";
        }
        model.addAttribute("squadra", squadraOptional.get());
        return "admin/squadre/form";
    }

    @PostMapping("/admin/squadre/{id}")
    public String aggiornaSquadra(@PathVariable("id") Long id, @ModelAttribute("squadra") Squadra squadraForm) {
        Squadra aggiornata = this.squadraService.aggiorna(id, squadraForm);
        return "redirect:/squadre/" + aggiornata.getId();
    }

    @PostMapping("/admin/squadre/{id}/elimina")
    public String eliminaSquadra(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            this.squadraService.elimina(id);
            return "redirect:/squadre";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("erroreEliminazione", e.getMessage());
            return "redirect:/squadre/" + id;
        }
    }
}