package it.uniroma3.siw.tornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.tornei.exception.UsernameGiaRegistratoException;
import it.uniroma3.siw.tornei.model.Utente;
import it.uniroma3.siw.tornei.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

	private UtenteService utenteService;

	public AuthenticationController(UtenteService utenteService) {
		this.utenteService = utenteService;
	}

	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("utente", new Utente());
		return "register";
	}

	@PostMapping("/register")
	public String registraUtente(@Valid @ModelAttribute("utente") Utente utente, BindingResult bindingResult) {
		//se non funziona:
		if (bindingResult.hasErrors()) {
			return "register";
		}
		//altrimenti:
		try {
			this.utenteService.registraNuovoUtente(utente);
			return "redirect:/login?registrato";
		} 
		//e se dovesse essere già registrato:
		catch (UsernameGiaRegistratoException e) {
			bindingResult.rejectValue("username", "utente.duplicate", "Questo username è già in uso");
			return "register";
		}
	}
}