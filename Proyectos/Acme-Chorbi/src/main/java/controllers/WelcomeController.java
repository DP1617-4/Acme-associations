/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SystemConfigurationService;
import domain.Actor;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}


	@Autowired
	SystemConfigurationService	scService;

	@Autowired
	ActorService				actorService;


	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name) {

		ModelAndView result;
		SimpleDateFormat formatter;
		String name1 = "guest";
		String moment;
		String banner;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		banner = this.scService.findRandomBanner();

		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		result = new ModelAndView("welcome/index");
		if (principal != "anonymousUser") {

			final Actor actor = this.actorService.findByPrincipal();
			name1 = actor.getName() + " " + actor.getSurname();
			result.addObject("actor", actor);
		}

		result.addObject("name", name1);
		result.addObject("moment", moment);
		result.addObject("banner", banner);

		return result;
	}
}
