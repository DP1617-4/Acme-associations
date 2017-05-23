
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SectionService;
import domain.Association;
import domain.Section;

@Controller
@RequestMapping("/section")
public class SectionController extends AbstractController {

	//Constructor

	public SectionController() {
		super();
	}


	//Service

	@Autowired
	private SectionService	sectionService;


	@RequestMapping(value = "/{association}/list", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable final Association association) {
		ModelAndView result;

		final Collection<Section> sections = this.sectionService.findAllByAssociation(association.getId());

		result = new ModelAndView("section/list");
		result.addObject("sections", sections);
		result.addObject("requestURI", "/section/" + association.getId() + "/list.do");

		return result;
	}

}
