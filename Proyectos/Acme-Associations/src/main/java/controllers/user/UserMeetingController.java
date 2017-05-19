
package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.MeetingService;
import services.RolesService;
import controllers.AbstractController;
import domain.Association;
import domain.Meeting;

@Controller
@RequestMapping("/user/meeting")
public class UserMeetingController extends AbstractController {

	public UserMeetingController() {
		super();
	}


	@Autowired
	private MeetingService	meetingService;

	@Autowired
	private RolesService	rolesService;


	@RequestMapping(value = "{association}/create", method = RequestMethod.GET)
	public ModelAndView create(@PathVariable final Association association) {
		ModelAndView result;
		final Meeting meeting = this.meetingService.create(association.getId());
		result = this.createEditModelAndView(meeting);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable final Meeting meeting, final RedirectAttributes redir) {
		ModelAndView result;
		Meeting m;

		m = this.meetingService.findOne(meeting.getId());

		try {
			this.rolesService.checkManagerPrincipal(m.getAssociation());
			result = this.createEditModelAndView(m);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", e.getMessage());
		}

		result = this.createEditModelAndView(meeting);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Meeting newMeeting, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(newMeeting);
		else
			try {
				this.rolesService.checkManagerPrincipal(newMeeting.getAssociation());
				newMeeting = this.meetingService.save(newMeeting);
				result = new ModelAndView("redirect:/meeting/" + newMeeting.getId() + "/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(newMeeting, "meeting.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "{association}/list", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable final Association association) {
		final ModelAndView result;

		this.rolesService.checkCollaboratorPrincipal(association);

		result = new ModelAndView("meeting/list");
		result.addObject("requestURI", "/user/meeting/list.do");

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
		result.addObject("errorMessage", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
