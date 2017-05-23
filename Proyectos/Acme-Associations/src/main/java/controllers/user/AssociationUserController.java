
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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
import forms.ChangeManager;

@Controller
@RequestMapping("/association/user")
public class AssociationUserController extends AbstractController {

	public AssociationUserController() {
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
			this.userService.findByPrincipal();

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

	@RequestMapping(value = "/changeManager", method = RequestMethod.POST, params = "save")
	public ModelAndView saveManager(final ChangeManager changeManager, final BindingResult binding, final RedirectAttributes redir) {
		final ModelAndView result;

		final User principal = this.userService.findByPrincipal();

		if(changeManager.getRole().equals(Roles.MANAGER)){
			this.rolesService.assignRoles(principal, changeManager.getAssociation(), "COLLABORATOR");
			this.rolesService.assignRoles(changeManager.getUser(), changeManager.getAssociation(), "MANAGER");
		}
		else{
			
			this.rolesService.assignRoles(changeManager.getUser(), changeManager.getAssociation(), changeManager.getRole());
		}
		result = new ModelAndView("redirect:/association/" + changeManager.getAssociation().getId() + "/display.do");
		redir.addFlashAttribute("flashMessage", "association.change.manager.correct");

		return result;
	}
	@RequestMapping(value = "/listOwn", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Collection<Roles> roles = this.rolesService.findAllByPrincipal();

		result = new ModelAndView("association/listOwn");
		result.addObject("roles", roles);
		result.addObject("requestURI", "/association/user/listOwn.do");

		return result;
	}

	@RequestMapping(value = "{association}/listUsers", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable final Association association) {
		final ModelAndView result;

		final Collection<Roles> roles = this.rolesService.findAllByAssociation(association);
		final Roles role = this.rolesService.findRolesByPrincipalAssociation(association);

		result = new ModelAndView("association/listUsers");
		result.addObject("roles", roles);
		result.addObject("role", role);
		result.addObject("requestURI", "/association/user/" + association.getId() + "/listUsers.do");

		return result;
	}

	@RequestMapping(value = "/{association}/changeManager", method = RequestMethod.GET)
	public ModelAndView changeManager(@PathVariable final Association association, final RedirectAttributes redir) {
		ModelAndView result;

		//Escoja una, hacer query en rolesRepo o hacer el método en association service.

		//		this.associationService.leave(association);
		try {
			this.rolesService.checkManagerPrincipal(association);

			Collection<User> users;
			users = this.userService.findAllByAssociation(association);
			User manager = this.userService.findAssociationManager(association);
			users.remove(manager);
			final ChangeManager changeManager = new ChangeManager();
			changeManager.setAssociation(association);

			result = new ModelAndView("association/changeManager");
			result.addObject("users", users);
			result.addObject("changeManager", changeManager);
			result.addObject("requestURI", "association/user/" + association.getId() + "/changeManager.do");
		} catch (final Exception e) {

			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", e.getMessage());
		}

		return result;
	}
	//En esta debería haber un mensaje de confirmación al acceder al enlace
	@RequestMapping(value = "/{association}/leave", method = RequestMethod.GET)
	public ModelAndView leave(@PathVariable final Association association, final RedirectAttributes redir) {
		ModelAndView result;

		//Escoja una, hacer query en rolesRepo o hacer el método en association service.

		//		this.associationService.leave(association);
		try {
			final Roles role = this.rolesService.findRolesByPrincipalAssociation(association);

			Assert.isTrue(role.getType() != "MANAGER");
			this.rolesService.delete(role);

			result = this.list();
			result.addObject("flashMessage", "association.left");
		} catch (final Exception e) {

			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", e.getMessage());
		}

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

		final String requestURI = "association/user/edit.do";

		result = new ModelAndView("association/edit");
		result.addObject("association", association);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
