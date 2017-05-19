
package controllers.user;

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
import services.SectionService;
import services.UserService;
import controllers.AbstractController;
import domain.Association;
import domain.Section;
import domain.User;

@Controller
@RequestMapping("/section/user")
public class SectionUserController extends AbstractController {

	public SectionUserController() {
		super();
	}


	@Autowired
	private AssociationService	associationService;

	@Autowired
	private SectionService		sectionService;

	@Autowired
	private UserService			userService;


	@RequestMapping(value = "/{association}/create", method = RequestMethod.GET)
	public ModelAndView create(@PathVariable final Association association) {
		ModelAndView result;
		final Section newSection = this.sectionService.create(association);
		result = this.createEditModelAndView(newSection);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sectionId, final RedirectAttributes redir) {
		ModelAndView result;
		final Section section;
		try {
			User user;
			user = this.userService.findByPrincipal();

			section = this.sectionService.findOne(sectionId);
			this.sectionService.checkResponsiblePrincipal(sectionId);

			result = this.createEditModelAndView(section);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", oops.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Section newSection, final BindingResult binding, final Association association) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(newSection);
		else
			try {
				newSection = this.sectionService.save(newSection);
				result = new ModelAndView("redirect:/section/" + association.getId() + "/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(newSection, "section.commit.error");
			}
		return result;
	}

	// Ancillary methods ---------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Section section) {
		ModelAndView result;

		result = this.createEditModelAndView(section, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Section section, final String message) {
		ModelAndView result;

		final String requestURI = "user/section/edit.do";
		final String cancelURI = "/section/" + section.getAssociation().getId() + "/list.do";

		result = new ModelAndView("section/edit");
		result.addObject("section", section);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", requestURI);
		result.addObject("cancelURI", cancelURI);

		return result;
	}

}
