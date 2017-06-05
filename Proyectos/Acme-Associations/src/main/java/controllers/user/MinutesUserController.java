
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MinutesService;
import services.RolesService;
import controllers.AbstractController;
import domain.Actor;
import domain.Association;
import domain.Meeting;
import domain.Minutes;
import domain.Roles;
import domain.User;
import forms.AddParticipant;

@Controller
@RequestMapping("minutes/user")
public class MinutesUserController extends AbstractController {

	public MinutesUserController() {
		super();
	}


	@Autowired
	private MinutesService	minutesService;

	@Autowired
	private RolesService	rolesService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "{association}/{meeting}/create", method = RequestMethod.GET)
	public ModelAndView create(@PathVariable final Association association, @PathVariable final Meeting meeting) {
		ModelAndView result;
		final Minutes minutesMeeting = this.minutesService.findOneByMeeting(meeting);
		if (minutesMeeting == null) {
			final Minutes minutes = this.minutesService.create(meeting.getId());

			Roles roles = null;
			String role = null;

			final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			final Actor actPrincipal = this.actorService.findByPrincipal();

			if (principal != "anonymousUser")
				if (actPrincipal instanceof User)
					roles = this.rolesService.findRolesByPrincipalAssociation(association);

			if (roles != null)
				role = roles.getType();

			result = this.createEditModelAndView(minutes);
			result.addObject("minutes", minutes);
		} else
			result = new ModelAndView("redirect:/meeting/user/" + association.getId() + "/" + meeting.getId() + "/display.do");
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Minutes minutes, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(minutes);
		else
			try {
				this.rolesService.checkManagerPrincipal(minutes.getMeeting().getAssociation());
				final Minutes newMinutes = this.minutesService.save(minutes);
				result = new ModelAndView("redirect:/meeting/user/" + newMinutes.getMeeting().getAssociation().getId() + "/" + newMinutes.getMeeting().getId() + "/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(minutes, "minutes.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/addParticipants", method = RequestMethod.POST, params = "save")
	public ModelAndView addPicture(final AddParticipant addParticipant, final BindingResult binding) {

		ModelAndView result;
		Minutes minute = addParticipant.getMinute();
		final Meeting meeting = minute.getMeeting();
		final Association association = meeting.getAssociation();
		final Collection<User> users = minute.getUsers();

		if (binding.hasErrors())
			result = this.createEditModelAndView(minute);
		else
			try {
				this.rolesService.checkManagerPrincipal(minute.getMeeting().getAssociation());
				this.minutesService.addParticipant(addParticipant.getUser().getId(), addParticipant.getMinute().getId());
				result = new ModelAndView("redirect:/meeting/user/" + association.getId() + "/" + meeting.getId() + "/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(minute, "minutes.commit.error");
			}
		return result;
	}

	// Ancillary methods ---------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Minutes minutes) {
		ModelAndView result;

		result = this.createEditModelAndView(minutes, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Minutes minutes, final String message) {
		ModelAndView result;

		final Meeting meeting = minutes.getMeeting();
		final Association association = meeting.getAssociation();

		final String requestURI = "minutes/user/edit.do";
		final String cancelURI = "meeting/user/" + association.getId() + "/" + meeting.getId() + "/display.do";

		result = new ModelAndView("minutes/edit");
		result.addObject("minutes", minutes);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", requestURI);
		result.addObject("cancelURI", cancelURI);

		return result;
	}

}
