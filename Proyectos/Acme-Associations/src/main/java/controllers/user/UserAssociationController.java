
package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AssociationService;
import services.RolesService;
import controllers.AbstractController;
import domain.Association;
import domain.Roles;

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


	@RequestMapping(value = "/listOwn", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Collection<Roles> roles = this.rolesService.findAllByPrincipal();

		result = new ModelAndView("association/list");
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

		result = new ModelAndView("redirect: /association/" + association.getId() + "/display.do");

		return result;
	}

	@RequestMapping(value = "/{association}/open", method = RequestMethod.GET)
	public ModelAndView open(@PathVariable final Association association) {
		ModelAndView result;

		this.associationService.closeAssociationByManager(association.getId());

		result = new ModelAndView("redirect: /association/" + association.getId() + "/display.do");

		return result;
	}

}
