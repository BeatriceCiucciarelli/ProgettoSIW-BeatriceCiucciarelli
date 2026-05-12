package it.uniroma3.siw.tornei.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.tornei.model.Torneo;
import it.uniroma3.siw.tornei.service.TorneoService;

@Controller
public class TorneoController {
	private TorneoService torneoService;
	
	/*COSTRUTTORE*/
	public TorneoController(TorneoService torneoService) {
		this.torneoService=torneoService;
		
	}
	
	
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
		List<Torneo> torneoList=this.torneoService.findAll();
		model.addAttribute("tornei", torneoList);
		return "tornei/list";
	}
}
