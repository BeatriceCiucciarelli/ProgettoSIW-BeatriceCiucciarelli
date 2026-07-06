package it.uniroma3.siw.tornei.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.tornei.model.Torneo;
import it.uniroma3.siw.tornei.service.TorneoService;

@Controller
public class TorneoController {

	private TorneoService torneoService;

	public TorneoController(TorneoService torneoService) {
		this.torneoService = torneoService;
	}

	// ===================== FUNZIONALITA' PUBBLICHE (Sezione 4.1) =====================

	@GetMapping("/tornei/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
	    Optional<Torneo> torneoOptional = this.torneoService.findById(id);
	    if (torneoOptional.isPresent()) {
	        model.addAttribute("torneo", torneoOptional.get());
	    }
	    return "tornei/show";
	}

	@GetMapping("/tornei")
	public String list(Model model) {
		List<Torneo> torneoList = this.torneoService.findAll();
		model.addAttribute("tornei", torneoList);
		return "tornei/list";
	}

	// ===================== FUNZIONALITA' ADMIN (Sezione 4.3) =====================
	// Protette da SecurityConfig tramite il pattern "/admin/**" -> hasAnyAuthority("ADMIN")

	@GetMapping("/admin/tornei/nuovo")
	public String formNuovoTorneo(Model model) {
		model.addAttribute("torneo", new Torneo());
		return "admin/tornei/form";
	}

	@PostMapping("/admin/tornei")
	public String creaTorneo(@ModelAttribute("torneo") Torneo torneo) {
		Torneo salvato = this.torneoService.salva(torneo);
		return "redirect:/tornei/" + salvato.getId();
	}

	@GetMapping("/admin/tornei/{id}/modifica")
	public String formModificaTorneo(@PathVariable("id") Long id, Model model) {
		Optional<Torneo> torneoOptional = this.torneoService.findById(id);
		if (torneoOptional.isEmpty()) {
			return "redirect:/tornei";
		}
		model.addAttribute("torneo", torneoOptional.get());
		return "admin/tornei/form";
	}

	@PostMapping("/admin/tornei/{id}")
	public String aggiornaTorneo(@PathVariable("id") Long id, @ModelAttribute("torneo") Torneo torneoForm) {
		Torneo aggiornato = this.torneoService.aggiorna(id, torneoForm);
		return "redirect:/tornei/" + aggiornato.getId();
	}
}