
package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AssociationService;
import controllers.AbstractController;
import domain.Association;

@Controller
@RequestMapping("/user/association")
public class UserAssociationController extends AbstractController {

	public UserAssociationController() {
		super();
	}


	@Autowired
	private AssociationService	associationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Association> associations = this.associationService.findAllByPrincipal();

		result = new ModelAndView("association/list");
		result.addObject("associations", associations);
		result.addObject("requestURI", "/user/association/list.do");

		return result;
	}

	//En esta debería haber un mensaje de confirmación al acceder al enlace
	@RequestMapping(value = "/{association}/leave", method = RequestMethod.GET)
	public ModelAndView leave(@PathVariable final Association association) {
		ModelAndView result;

		//Escoja una, hacer query en rolesRepo o hacer el método en association service.

		//		this.associationService.leave(association);
		//		Role role = this.rolesService.findByAssociationAndPrincipal(association);
		//		this.rolesService.delete(role);

		result = this.list();
		result.addObject("flashMessage", "association.left");

		return result;
	}

	@RequestMapping(value = "/{association}/close", method = RequestMethod.GET)
	public ModelAndView close(@PathVariable final Association association) {
		ModelAndView result;

		this.associationService.close(association);

		result = new ModelAndView("redirect: /association/" + association.getId() + "/display.do");

		return result;
	}

	@RequestMapping(value = "/{association}/open", method = RequestMethod.GET)
	public ModelAndView open(@PathVariable final Association association) {
		ModelAndView result;

		this.associationService.open(association);

		result = new ModelAndView("redirect: /association/" + association.getId() + "/display.do");

		return result;
	}

}
