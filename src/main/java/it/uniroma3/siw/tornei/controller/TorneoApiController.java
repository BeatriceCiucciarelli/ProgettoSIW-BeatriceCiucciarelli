package it.uniroma3.siw.tornei.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.tornei.dto.ClassificaApiDTO;
import it.uniroma3.siw.tornei.dto.TorneoSummaryDTO;
import it.uniroma3.siw.tornei.model.Torneo;
import it.uniroma3.siw.tornei.service.TorneoService;

/*
 * Controller REST usato dal frontend React separato (Vite, porta 5173).
 * Restituisce sempre JSON, mai nomi di template Thymeleaf.
 */
@RestController
@RequestMapping("/api/tornei")
public class TorneoApiController {

    private final TorneoService torneoService;

    public TorneoApiController(TorneoService torneoService) {
        this.torneoService = torneoService;
    }

    @GetMapping
    public List<TorneoSummaryDTO> elencoTornei() {
        return this.torneoService.findAll().stream()
            .map(this::mappaSummary)
            .toList();
    }

    @GetMapping("/{id}/classifica")
    public List<ClassificaApiDTO> classifica(@PathVariable("id") Long id) {
        return this.torneoService.calcolaClassificaApi(id);
    }

    private TorneoSummaryDTO mappaSummary(Torneo torneo) {
        TorneoSummaryDTO dto = new TorneoSummaryDTO();
        dto.setId(torneo.getId());
        dto.setNome(torneo.getNome());
        dto.setAnno(torneo.getAnno());
        return dto;
    }
}