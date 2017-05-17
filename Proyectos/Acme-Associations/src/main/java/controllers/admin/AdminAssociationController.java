
package controllers.admin;

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
@RequestMapping("association/administrator")
public class AdminAssociationController extends AbstractController {

	public AdminAssociationController() {
		super();
	}


	@Autowired
	private AssociationService	associationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Association> associations = this.associationService.findAll();

		result = new ModelAndView("association/list");
		result.addObject("associations", associations);
		result.addObject("requestURI", "/admin/association/list.do");

		return result;
	}

	@RequestMapping(value = "/{association}/ban", method = RequestMethod.GET)
	public ModelAndView ban(@PathVariable final Association association) {
		ModelAndView result;

		this.associationService.banAssociation(association.getId());

		result = this.list();
		result.addObject("flashMessage", "association.ban");
		return result;
	}

}
