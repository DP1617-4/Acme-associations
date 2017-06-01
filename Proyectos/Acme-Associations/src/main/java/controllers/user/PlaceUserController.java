
package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.PlaceService;
import services.RolesService;
import controllers.AbstractController;
import domain.Activity;
import domain.Place;

@Controller
@RequestMapping("/place/user")
public class PlaceUserController extends AbstractController {

	public PlaceUserController() {
		super();
	}


	@Autowired
	private PlaceService	placeService;

	@Autowired
	private RolesService	rolesService;


	@RequestMapping(value = "/{activity}/create", method = RequestMethod.GET)
	public ModelAndView create(@PathVariable final Activity activity, RedirectAttributes redir) {
		ModelAndView result;
		try {

			this.rolesService.checkCollaboratorPrincipal(activity.getAssociation());
			final Place newPlace = this.placeService.create();
			result = this.createEditModelAndView(newPlace, activity);
		} catch (Exception e) {

			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", e.getMessage());
		}
		return result;
	}
	@RequestMapping(value = "/{activity}/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Place place, final BindingResult binding, @PathVariable final Activity activity) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(place, activity);
			result.addObject("errorMessage2", "address.input.none");
		} else
			try {
				place = this.placeService.save(place, activity);
				result = new ModelAndView("redirect:/activity/" + activity.getAssociation().getId() + "/" + activity.getId() + "/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(place, activity, oops.getMessage());
			}
		return result;
	}
	// Ancillary methods ---------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Place place, final Activity activity) {
		ModelAndView result;

		result = this.createEditModelAndView(place, activity, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Place place, final Activity activity, final String message) {
		ModelAndView result;

		final String requestURI = "place/user/" + activity.getId() + "/edit.do";

		final String cancelURI = "activity/" + activity.getAssociation().getId() + "/" + activity.getId() + "/display.do";

		result = new ModelAndView("place/edit");
		result.addObject("place", place);
		result.addObject("errorMessage", message);
		result.addObject("cancelURI", cancelURI);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
