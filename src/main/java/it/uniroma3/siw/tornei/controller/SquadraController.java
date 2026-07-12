package it.uniroma3.siw.tornei.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.tornei.model.Squadra;
import it.uniroma3.siw.tornei.model.Torneo;
import it.uniroma3.siw.tornei.service.SquadraService;
import jakarta.validation.Valid;

@Controller
public class SquadraController {

    private final SquadraService squadraService;

    public SquadraController(SquadraService squadraService) {
        this.squadraService = squadraService;
    }

    @GetMapping("/squadre")
    public String list(Model model) {
        List<Squadra> squadre = this.squadraService.findAll();
        model.addAttribute("squadre", squadre);
        return "squadre/list";
    }

    @GetMapping("/squadre/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<Squadra> squadraOptional = this.squadraService.findById(id);
        if (squadraOptional.isEmpty()) {
            return "redirect:/squadre";
        }
        model.addAttribute("squadra", squadraOptional.get());
        return "squadre/show";
    }

    @GetMapping("/admin/squadre/nuova")
    public String formNuovaSquadra(Model model) {
        model.addAttribute("squadra", new Squadra());
        return "admin/squadre/form";
    }

    @PostMapping("/admin/squadre")
    public String creaSquadra(@Valid @ModelAttribute("squadra") Squadra squadra, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/squadre/form";
        }
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
    public String aggiornaSquadra(@PathVariable("id") Long id,
                                   @Valid @ModelAttribute("squadra") Squadra squadraForm,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            squadraForm.setId(id);
            return "admin/squadre/form";
        }
        Squadra aggiornata = this.squadraService.aggiorna(id, squadraForm);
        return "redirect:/squadre/" + aggiornata.getId();
    }

    @PostMapping("/admin/squadre/{id}/elimina")
    public String eliminaSquadra(@PathVariable("id") Long id) {
        this.squadraService.elimina(id);
        return "redirect:/squadre";
    }
}