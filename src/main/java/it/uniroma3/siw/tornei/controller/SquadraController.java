package it.uniroma3.siw.tornei.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.tornei.model.Squadra;
import it.uniroma3.siw.tornei.service.SquadraService;

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
        if (squadraOptional.isPresent()) {
            model.addAttribute("squadra", squadraOptional.get());
        }
        return "squadre/show";
    }
}