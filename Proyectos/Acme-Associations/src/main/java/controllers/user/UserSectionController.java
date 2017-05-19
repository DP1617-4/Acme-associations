
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

import services.SectionService;
import services.UserService;
import controllers.AbstractController;
import domain.Association;
import domain.Section;
import domain.User;

@Controller
@RequestMapping("/section/user")
public class UserSectionController extends AbstractController {

	public UserSectionController() {
		super();
	}


	@Autowired
	private SectionService	sectionService;

	@Autowired
	private UserService		userService;


	@RequestMapping(value = "/{association}/create", method = RequestMethod.GET)
	public ModelAndView create(@PathVariable final Association association) {
		ModelAndView result;
		Collection<User> users;
		users = this.userService.findAssociationCollaboratorsAndManager(association);
		final Section newSection = this.sectionService.create(association);
		result = this.createEditModelAndView(newSection, association);
		result.addObject("users", users);
		return result;
	}
	@RequestMapping(value = "/{association}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sectionId, final RedirectAttributes redir, @PathVariable final Association association) {
		ModelAndView result;
		final Section section;
		try {
			section = this.sectionService.findOne(sectionId);
			this.sectionService.checkResponsiblePrincipal(sectionId);

			result = this.createEditModelAndView(section, association);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", oops.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/{association}/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Section newSection, final BindingResult binding, @PathVariable final Association association) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(newSection, association);
		else
			try {
				newSection = this.sectionService.save(newSection);
				result = new ModelAndView("redirect:/section/" + association.getId() + "/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(newSection, association, "section.commit.error");
			}
		return result;
	}

	// Ancillary methods ---------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Section section, final Association association) {
		ModelAndView result;

		result = this.createEditModelAndView(section, association, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Section section, final Association association, final String message) {
		ModelAndView result;

		final String requestURI = "section/user/" + association.getId() + "/edit.do";
		final String cancelURI = "section/" + association.getId() + "/list.do";

		Collection<User> users;
		users = this.userService.findAssociationCollaboratorsAndManager(association);

		result = new ModelAndView("section/edit");
		result.addObject("section", section);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", requestURI);
		result.addObject("cancelURI", cancelURI);
		result.addObject("users", users);

		return result;
	}

}
