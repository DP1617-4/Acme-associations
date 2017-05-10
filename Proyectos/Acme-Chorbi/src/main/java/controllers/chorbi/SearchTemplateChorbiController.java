
package controllers.chorbi;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.SearchTemplateService;
import services.SystemConfigurationService;
import controllers.AbstractController;
import domain.Chorbi;
import domain.SearchTemplate;
import domain.SystemConfiguration;

@Controller
@RequestMapping("/searchTemplate/chorbi")
public class SearchTemplateChorbiController extends AbstractController {

	//Services --------------------
	@Autowired
	private SearchTemplateService		searchTemplateService;

	@Autowired
	private ChorbiService				chorbiService;

	@Autowired
	private SystemConfigurationService	scService;


	//Constructor -----------------
	public SearchTemplateChorbiController() {
		super();
	}

	//Creation -------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final SearchTemplate searchTemplate = this.searchTemplateService.create();
		result = this.createEditModelAndView(searchTemplate);
		return result;
	}

	//Edition --------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		final Chorbi principal = this.chorbiService.findByPrincipal();
		final SearchTemplate searchTemplate = this.searchTemplateService.findSearchTemplateByChorbi(principal);
		ModelAndView result;
		if (searchTemplate == null)
			result = new ModelAndView("redirect:/searchTemplate/chorbi/create.do");
		else
			result = this.createEditModelAndView(searchTemplate, null);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final SearchTemplate searchTemplate, final BindingResult binding) {
		ModelAndView result;
		Boolean sameFields;

		SearchTemplate search;
		try {
			sameFields = this.searchTemplateService.checkCache(searchTemplate);
			search = this.searchTemplateService.reconstruct(searchTemplate, binding);

			final SystemConfiguration system = this.scService.findMain();
			final DateTime last = new DateTime(search.getMoment());
			final DateTime now = DateTime.now();

			if (binding.hasErrors())
				result = this.createEditModelAndView(searchTemplate);
			else
				try {

					if (now.minus(system.getCacheTime().getTime()).isBefore(last) && sameFields) {
						result = new ModelAndView("redirect:/chorbi/chorbi/listFound.do?searchTemplateId=" + search.getId());
						result.addObject("message", "searchTemplate.commit.ok");
					} else
						try {
							final SearchTemplate searched = this.searchTemplateService.save(search);
							result = new ModelAndView("redirect:/chorbi/chorbi/listFound.do?searchTemplateId=" + search.getId());
							result.addObject("message", "searchTemplate.commit.ok");
						} catch (NullPointerException | IllegalArgumentException e) {
							result = new ModelAndView("redirect:/creditCard/chorbi/edit.do");
						}

				} catch (final Throwable oops) {
					result = this.createEditModelAndView(searchTemplate, "searchTemplate.commit.error");
				}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(searchTemplate, "searchTemplate.commit.error");
		}

		return result;

	}

	//Ancillary methods -------------
	protected ModelAndView createEditModelAndView(final SearchTemplate searchTemplate) {
		return this.createEditModelAndView(searchTemplate, null);
	}

	protected ModelAndView createEditModelAndView(final SearchTemplate searchTemplate, final String message) {
		ModelAndView result;

		result = new ModelAndView("searchTemplate/edit");
		result.addObject("searchTemplate", searchTemplate);
		result.addObject("message", message);

		return result;
	}

}
