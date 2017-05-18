
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.AssociationService;
import services.RolesService;
import services.UserService;
import controllers.AbstractController;
import domain.Association;
import domain.Roles;
import domain.User;

@Controller
@RequestMapping("/user/association")
public class UserAssociationController extends AbstractController {

	public UserAssociationController() {
		super();
	}


	@Autowired
	private AssociationService	associationService;

	@Autowired
	private RolesService		rolesService;

	@Autowired
	private UserService			userService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Association newAssociation = this.associationService.create();
		result = this.createEditModelAndView(newAssociation);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int associationId, final RedirectAttributes redir) {
		ModelAndView result;
		Association association;
		try {
			final User user = this.userService.findByPrincipal();

			association = this.associationService.findOne(associationId);
			this.rolesService.checkManagerPrincipal(association);

			result = this.createEditModelAndView(association);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", oops.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Association newAssociation, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(newAssociation);
		else
			try {
				newAssociation = this.associationService.save(newAssociation);
				result = new ModelAndView("redirect:/association/" + newAssociation.getId() + "/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(newAssociation, "association.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/listOwn", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Collection<Roles> roles = this.rolesService.findAllByPrincipal();

		result = new ModelAndView("association/listOwn");
		result.addObject("roles", roles);
		result.addObject("requestURI", "/user/association/listOwn.do");

		return result;
	}
	//En esta debería haber un mensaje de confirmación al acceder al enlace
	@RequestMapping(value = "/{association}/leave", method = RequestMethod.GET)
	public ModelAndView leave(@PathVariable final Association association) {
		ModelAndView result;

		//Escoja una, hacer query en rolesRepo o hacer el método en association service.

		//		this.associationService.leave(association);
		final Roles role = this.rolesService.findRolesByPrincipalAssociation(association);
		this.rolesService.delete(role);

		result = this.list();
		result.addObject("flashMessage", "association.left");

		return result;
	}

	@RequestMapping(value = "/{association}/close", method = RequestMethod.GET)
	public ModelAndView close(@PathVariable final Association association) {
		ModelAndView result;

		this.associationService.closeAssociationByManager(association.getId());

		result = new ModelAndView("redirect:/association/" + association.getId() + "/display.do");

		return result;
	}

	@RequestMapping(value = "/{association}/open", method = RequestMethod.GET)
	public ModelAndView open(@PathVariable final Association association) {
		ModelAndView result;

		this.associationService.closeAssociationByManager(association.getId());

		result = new ModelAndView("redirect:/association/" + association.getId() + "/display.do");

		return result;
	}

	// Ancillary methods ---------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Association association) {
		ModelAndView result;

		result = this.createEditModelAndView(association, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Association association, final String message) {
		ModelAndView result;

		final String requestURI = "user/association/edit.do";

		result = new ModelAndView("association/edit");
		result.addObject("association", association);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
