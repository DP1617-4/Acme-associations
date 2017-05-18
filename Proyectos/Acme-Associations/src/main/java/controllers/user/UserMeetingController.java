
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

import services.AssociationService;
import services.MeetingService;
import services.RolesService;
import services.UserService;
import controllers.AbstractController;
import domain.Association;
import domain.Meeting;
import domain.Roles;
import domain.User;

@Controller
@RequestMapping("/user/meeting")
public class UserMeetingController extends AbstractController {

	public UserMeetingController() {
		super();
	}


	@Autowired
	private MeetingService		meetingService;

	@Autowired
	private AssociationService	associationService;

	@Autowired
	private RolesService		rolesService;

	@Autowired
	private UserService			userService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@PathVariable final Association association) {
		ModelAndView result;
		final Meeting meeting = this.meetingService.create(association.getId());
		result = this.createEditModelAndView(meeting);
		result.addObject("meeting", meeting);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int associationId) {
		ModelAndView result;
		Association association;
		final User user = this.userService.findByPrincipal();

		association = this.associationService.findOne(associationId);
		this.rolesService.checkManager(user, association);

		result = this.createEditModelAndView(association);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Association association, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(association);
		else
			try {
				final User user = this.userService.findByPrincipal();
				this.rolesService.checkManager(user, association);
				association = this.associationService.save(association);
				result = new ModelAndView("redirect:/association/" + association.getId() + "/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(association, "association.commit.error");
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
	protected ModelAndView createEditModelAndView(final Meeting meeting) {
		ModelAndView result;

		result = this.createEditModelAndView(meeting, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Meeting meeting, final String message) {
		ModelAndView result;

		final String requestURI = "meeting/edit.do";

		result = new ModelAndView("meeting/edit");
		result.addObject("meeting", meeting);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
