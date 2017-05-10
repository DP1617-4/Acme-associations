
package controllers.chorbi;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.CreditCardService;
import services.LikesService;
import services.SearchTemplateService;
import domain.Chorbi;
import domain.CreditCard;
import domain.SearchTemplate;

@Controller
@RequestMapping("/chorbi/chorbi")
public class ChorbiChorbiController {

	//Services

	@Autowired
	private ChorbiService			chorbiService;

	@Autowired
	private LikesService			likesService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private SearchTemplateService	stService;


	//Constructor

	public ChorbiChorbiController() {
		super();
	}

	//Register

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Chorbi chorbi;

		chorbi = this.chorbiService.findByPrincipal();
		result = this.createEditModelAndView(chorbi);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;

		Chorbi chorbi;

		chorbi = this.chorbiService.findByPrincipal();

		result = new ModelAndView("chorbi/display");
		result.addObject("chorbi", chorbi);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Chorbi editChorbi, final BindingResult binding) {
		ModelAndView result;
		Chorbi chorbi;

		chorbi = editChorbi;
		if (binding.hasErrors())
			result = this.createEditModelAndView(editChorbi);
		else
			try {
				chorbi = this.chorbiService.save(chorbi);
				result = new ModelAndView("redirect:/chorbi/chorbi/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(editChorbi, "chorbi.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/listLiking", method = RequestMethod.GET)
	public ModelAndView listLiking(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Chorbi> chorbis;
		Collection<Chorbi> likes;
		final Chorbi principal = this.chorbiService.findByPrincipal();
		final CreditCard credit = this.creditCardService.findByPrincipal();
		if (credit != null && this.creditCardService.checkCCNumber(credit.getCreditCardNumber()) && this.creditCardService.expirationDate(credit)) {

			chorbis = this.chorbiService.findAllLikingMe(principal.getId());
			likes = this.chorbiService.findAllLiked(principal.getId());

			result = new ModelAndView("chorbi/list");
			result.addObject("chorbis", chorbis);
			result.addObject("likes", likes);
			result.addObject("requestURI", "chorbi/chorbi/listLiking.do");
		} else
			result = new ModelAndView("redirect:/creditCard/chorbi/edit.do");

		return result;
	}
	@RequestMapping(value = "/listFound", method = RequestMethod.GET)
	public ModelAndView listFound(@RequestParam(required = false, defaultValue = "0") final int searchTemplateId) {

		ModelAndView result;
		Collection<Chorbi> chorbies;
		Collection<Chorbi> likes;
		final Chorbi principal = this.chorbiService.findByPrincipal();
		final CreditCard credit = this.creditCardService.findByPrincipal();
		if (credit != null) {
			if (this.creditCardService.checkCCNumber(credit.getCreditCardNumber()) && this.creditCardService.expirationDate(credit)) {
				if (searchTemplateId == 0) {
					final SearchTemplate search = this.stService.findSearchTemplateByChorbi(principal);
					if (search == null)
						result = new ModelAndView("redirect:/searchTemplate/chorbi/create.do");
					else {
						chorbies = this.chorbiService.findAllFound(search.getId());
						chorbies.remove(principal);
						likes = this.chorbiService.findAllLiked(principal.getId());
						result = new ModelAndView("chorbi/list");
						result.addObject("chorbis", chorbies);
						result.addObject("likes", likes);
						result.addObject("requestURI", "chorbi/chorbi/listFound.do");
					}
				} else {

					chorbies = this.chorbiService.findAllFound(searchTemplateId);
					chorbies.remove(principal);
					likes = this.chorbiService.findAllLiked(principal.getId());
					result = new ModelAndView("chorbi/list");
					result.addObject("chorbis", chorbies);
					result.addObject("likes", likes);
					result.addObject("requestURI", "chorbi/chorbi/listFound.do");
				}

			} else
				result = new ModelAndView("redirect:/creditCard/chorbi/edit.do");

		} else
			result = new ModelAndView("redirect:/creditCard/chorbi/edit.do");

		return result;

	}
	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Chorbi chorbi) {
		ModelAndView result;

		result = this.createEditModelAndView(chorbi, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Chorbi chorbi, final String message) {
		ModelAndView result;

		final String requestURI = "chorbi/chorbi/edit.do";

		result = new ModelAndView("chorbi/edit");
		result.addObject("chorbi", chorbi);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
