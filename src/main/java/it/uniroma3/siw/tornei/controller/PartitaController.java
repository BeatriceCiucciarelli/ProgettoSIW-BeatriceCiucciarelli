package it.uniroma3.siw.tornei.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.tornei.model.Partita;
import it.uniroma3.siw.tornei.service.PartitaService;

@Controller
public class PartitaController {

    private final PartitaService partitaService;

    public PartitaController(PartitaService partitaService) {
        this.partitaService = partitaService;
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
        if (partitaOptional.isPresent()) {
            model.addAttribute("partita", partitaOptional.get());
        }
        return "partite/show";
    }
}