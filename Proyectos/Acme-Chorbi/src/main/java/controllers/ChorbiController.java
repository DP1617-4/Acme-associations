
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChorbiService;
import services.LikesService;
import domain.Actor;
import domain.Chorbi;
import forms.RegisterChorbi;

@Controller
@RequestMapping("/chorbi")
public class ChorbiController {

	//Services

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private LikesService	likesService;

	@Autowired
	private ActorService	actorService;


	//Constructor

	public ChorbiController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Chorbi> chorbis;
		Actor principal;

		result = new ModelAndView("chorbi/list");

		principal = this.actorService.findByPrincipal();
		final Collection<Chorbi> chorbies = this.chorbiService.findAllNotBanned();
		if (principal instanceof Chorbi) {
			chorbis = this.chorbiService.findAllLiked(principal.getId());
			result.addObject("likes", chorbis);
		}

		result.addObject("chorbis", chorbies);
		result.addObject("requestURI", "chorbi/list.do");
		return result;

	}

	//Register

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		RegisterChorbi registerChorbi;

		registerChorbi = new RegisterChorbi();
		result = this.createEditModelAndView(registerChorbi);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RegisterChorbi registerChorbi, final BindingResult binding) {
		ModelAndView result;
		Chorbi chorbi;

		chorbi = this.chorbiService.reconstruct(registerChorbi, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(registerChorbi);
		else
			try {
				chorbi = this.chorbiService.register(chorbi);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				registerChorbi.setAccept(false);
				result = this.createEditModelAndView(registerChorbi, "chorbi.commit.error");
			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final RegisterChorbi registerChorbi) {
		ModelAndView result;

		result = this.createEditModelAndView(registerChorbi, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final RegisterChorbi registerChorbi, final String message) {
		ModelAndView result;

		final String requestURI = "chorbi/edit.do";

		result = new ModelAndView("chorbi/register");
		result.addObject("registerChorbi", registerChorbi);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
