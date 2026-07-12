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

import it.uniroma3.siw.tornei.model.Torneo;
import it.uniroma3.siw.tornei.service.TorneoService;
import jakarta.validation.Valid;

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
	    if (torneoOptional.isEmpty()) {
	        return "redirect:/tornei";
	    }
	    model.addAttribute("torneo", torneoOptional.get());
	    return "tornei/show";
	}

	@GetMapping("/tornei")
	public String list(Model model) {
		List<Torneo> torneoList = this.torneoService.findAll();
		model.addAttribute("tornei", torneoList);
		return "tornei/list";
	}

	@GetMapping("/tornei/{id}/classifica")
	public String classifica(@PathVariable("id") Long id, Model model) {
		Optional<Torneo> torneoOptional = this.torneoService.findById(id);
		if (torneoOptional.isEmpty()) {
			return "redirect:/tornei";
		}
		model.addAttribute("torneo", torneoOptional.get());
		model.addAttribute("classifica", this.torneoService.calcolaClassifica(id));
		return "tornei/classifica";
	}

	@GetMapping("/tornei/{id}/classifica-react")
	public String classificaReact(@PathVariable("id") Long id, Model model) {
		Optional<Torneo> torneoOptional = this.torneoService.findById(id);
		if (torneoOptional.isEmpty()) {
			return "redirect:/tornei";
		}
		model.addAttribute("torneo", torneoOptional.get());
		return "tornei/classifica-react";
	}

	// ===================== FUNZIONALITA' ADMIN (Sezione 4.3) =====================

	@GetMapping("/admin/tornei/nuovo")
	public String formNuovoTorneo(Model model) {
		model.addAttribute("torneo", new Torneo());
		return "admin/tornei/form";
	}

	/*
	 * @Valid attiva la validazione Bean Validation sull'oggetto Torneo,
	 * seguendo le annotazioni (@NotBlank, @NotNull, ecc.) messe sui campi.
	 * BindingResult DEVE essere il parametro SUBITO DOPO l'oggetto validato,
	 * altrimenti Spring lancia un'eccezione invece di raccogliere gli errori.
	 * Se ci sono errori, invece di salvare si ri-mostra lo stesso form,
	 * cosi' l'utente vede cosa correggere senza perdere i dati inseriti.
	 */
	@PostMapping("/admin/tornei")
	public String creaTorneo(@Valid @ModelAttribute("torneo") Torneo torneo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "admin/tornei/form";
		}
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
	public String aggiornaTorneo(@PathVariable("id") Long id,
	                              @Valid @ModelAttribute("torneo") Torneo torneoForm,
	                              BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// per far ri-comparire correttamente il link "modifica" nel form
			// serve che l'id sia presente anche in caso di errore
			torneoForm.setId(id);
			return "admin/tornei/form";
		}
		Torneo aggiornato = this.torneoService.aggiorna(id, torneoForm);
		return "redirect:/tornei/" + aggiornato.getId();
	}
}