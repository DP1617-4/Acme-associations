
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AssociationService;
import domain.Association;

@Controller
@RequestMapping("/association")
public class AssociationController extends AbstractController {

	//Constructor

	public AssociationController() {
		super();
	}


	//Service

	@Autowired
	private AssociationService	associationService;


	@RequestMapping(value = "/{association}/display", method = RequestMethod.GET)
	public ModelAndView display(@PathVariable final Association association) {
		ModelAndView result;

		result = new ModelAndView("association/display");
		result.addObject("association", association);
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Association> associations = this.associationService.findAllExceptBannedAndClosed();

		result = new ModelAndView("association/list");
		result.addObject("associations", associations);
		result.addObject("requestURI", "/association/list.do");

		return result;
	}
}
