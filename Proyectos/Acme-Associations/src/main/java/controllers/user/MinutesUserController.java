
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable final Minutes minutes, @PathVariable final Association association, final RedirectAttributes redir) {
		ModelAndView result;
		Minutes m;

		Roles roles = null;
		String role = null;

		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final Actor actPrincipal = this.actorService.findByPrincipal();

		if (principal != "anonymousUser")
			if (actPrincipal instanceof User)
				roles = this.rolesService.findRolesByPrincipalAssociation(association);

		if (roles != null)
			role = roles.getType();

		m = this.minutesService.findOne(minutes.getId());
		final Association ass = m.getMeeting().getAssociation();

		try {
			this.rolesService.checkManagerPrincipal(ass);
			result = this.createEditModelAndView(m);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", e.getMessage());
		}

		result = this.createEditModelAndView(minutes);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Minutes newMinutes, @PathVariable final Association association, final BindingResult binding) {
		ModelAndView result;

		Roles roles = null;
		String role = null;

		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final Actor actPrincipal = this.actorService.findByPrincipal();

		if (principal != "anonymousUser")
			if (actPrincipal instanceof User)
				roles = this.rolesService.findRolesByPrincipalAssociation(association);

		if (roles != null)
			role = roles.getType();

		if (binding.hasErrors())
			result = this.createEditModelAndView(newMinutes);
		else
			try {
				this.rolesService.checkManagerPrincipal(newMinutes.getMeeting().getAssociation());
				newMinutes = this.minutesService.save(newMinutes);
				result = new ModelAndView("redirect:/minutes/" + newMinutes.getId() + "/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(newMinutes, "minutes.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@PathVariable final Association association, @PathVariable final Meeting meeting) {
		final ModelAndView result;

		Roles roles = null;
		String role = null;

		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final Actor actPrincipal = this.actorService.findByPrincipal();

		if (principal != "anonymousUser")
			if (actPrincipal instanceof User)
				roles = this.rolesService.findRolesByPrincipalAssociation(association);

		if (roles != null)
			role = roles.getType();

		this.rolesService.checkCollaboratorPrincipal(association);
		final Minutes minutes = this.minutesService.findOneByMeeting(meeting);

		result = new ModelAndView("minutes/display");
		result.addObject("minutes", minutes);
		result.addObject("requestURI", "/minutes/user/" + association.getId() + "/" + meeting.getId() + "/display.do");

		return result;
	}

	@RequestMapping(value = "/addParticipants", method = RequestMethod.POST, params = "save")
	public ModelAndView addPicture(@Valid final AddParticipant addParticipant, final BindingResult binding) {

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
				users.add(addParticipant.getUser());
				minute.setUsers(users);
				minute = this.minutesService.save(minute);
				result = new ModelAndView("redirect: /meeting/user/" + association.getId() + "/" + meeting.getId() + "/display.do");
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

		final String requestURI = "minutes/edit.do";
		final String cancelURI = "meeting/user/" + association.getId() + "/" + meeting.getId() + "/display.do";

		result = new ModelAndView("minutes/edit");
		result.addObject("minutes", minutes);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", requestURI);
		result.addObject("cancelURI", cancelURI);

		return result;
	}

}
