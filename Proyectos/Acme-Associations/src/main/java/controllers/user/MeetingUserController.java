
package controllers.user;

import java.util.Collection;
import java.util.Date;

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
import services.CommentService;
import services.MeetingService;
import services.MinutesService;
import services.RolesService;
import services.UserService;
import controllers.AbstractController;
import domain.Actor;
import domain.Association;
import domain.Comment;
import domain.Meeting;
import domain.Minutes;
import domain.Roles;
import domain.User;
import forms.AddParticipant;

@Controller
@RequestMapping("/meeting/user")
public class MeetingUserController extends AbstractController {

	public MeetingUserController() {
		super();
	}


	@Autowired
	private MeetingService	meetingService;

	@Autowired
	private MinutesService	minutesService;

	@Autowired
	private RolesService	rolesService;

	@Autowired
	private CommentService	commentService;

	@Autowired
	private UserService		userService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "{association}/create", method = RequestMethod.GET)
	public ModelAndView create(@PathVariable final Association association) {
		ModelAndView result;
		this.userService.findByPrincipal();
		this.rolesService.checkManagerPrincipal(association);
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
	public ModelAndView save(@Valid final Meeting meeting, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(meeting);
		else
			try {
				this.rolesService.checkManagerPrincipal(meeting.getAssociation());
				final Meeting newMeeting = this.meetingService.save(meeting);
				final Association association = newMeeting.getAssociation();
				result = new ModelAndView("redirect:/meeting/user/" + association.getId() + "/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(meeting, "meeting.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/{association}/{meeting}/display", method = RequestMethod.GET)
	public ModelAndView display(@PathVariable final Association association, final Meeting meeting) {
		ModelAndView result;

		Boolean esAnterior = false;
		final Date actual = new Date();
		final Minutes minute = this.minutesService.findOneByMeeting(meeting);

		Collection<Comment> comments;
		comments = this.commentService.findAllByCommentableId(meeting.getId());
		final Comment comment = this.commentService.create(meeting.getId());

		if (meeting.getMoment().before(actual))
			esAnterior = true;

		Roles roles = null;
		String role = null;

		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		result = new ModelAndView("welcome/index");
		if (principal != "anonymousUser") {
			final Actor actPrincipal = this.actorService.findByPrincipal();
			if (actPrincipal instanceof User)
				roles = this.rolesService.findRolesByPrincipalAssociation(association);
		}

		if (roles != null)
			role = roles.getType();

		result = new ModelAndView("meeting/display");
		result.addObject("association", association);
		result.addObject("meeting", meeting);
		result.addObject("esAnterior", esAnterior);
		result.addObject("comments", comments);
		result.addObject("comment", comment);
		result.addObject("role", role);
		result.addObject("minutes", minute);

		if (minute != null) {
			Collection<Comment> commentsSecond;
			final AddParticipant addParticipant = new AddParticipant();
			commentsSecond = this.commentService.findAllByCommentableId(minute.getId());
			final Comment commentSecond = this.commentService.create(minute.getId());
			final Collection<User> users = this.userService.findAssociationCollaboratorsAndManager(association);
			final Collection<User> participants = minute.getUsers();
			users.removeAll(minute.getUsers());
			final User principalUser = this.userService.findByPrincipal();
			Boolean isParticipant = false;
			if (participants.contains(principalUser))
				isParticipant = true;

			addParticipant.setMinute(minute);
			result.addObject("minute", minute);
			result.addObject("commentsSecond", commentsSecond);
			result.addObject("commentSecond", commentSecond);
			result.addObject("addParticipant", addParticipant);
			result.addObject("users", users);
			result.addObject("isParticipant", isParticipant);
			result.addObject("participants", participants);
		}

		result.addObject("requestURI", "/meeting/user/" + association.getId() + "/" + meeting.getId() + "/display.do");
		return result;

	}

	@RequestMapping(value = "{association}/list", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable final Association association) {
		final ModelAndView result;
		Roles roles = null;
		String role = null;

		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal != "anonymousUser") {
			final Actor actPrincipal = this.actorService.findByPrincipal();
			this.rolesService.checkCollaboratorPrincipal(association);
			if (actPrincipal instanceof User)
				roles = this.rolesService.findRolesByPrincipalAssociation(association);
		}
		if (roles != null)
			role = roles.getType();

		final Collection<Meeting> meetings = this.meetingService.findAllByAssociation(association);

		result = new ModelAndView("meeting/list");
		result.addObject("meetings", meetings);
		result.addObject("role", role);
		result.addObject("requestURI", "/meeting/user/" + association.getId() + "/list.do");

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
		final Association association = meeting.getAssociation();

		final String requestURI = "meeting/user/edit.do";
		final String cancelURI = "meeting/user/" + association.getId() + "/list.do";

		result = new ModelAndView("meeting/edit");
		result.addObject("meeting", meeting);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", requestURI);
		result.addObject("cancelURI", cancelURI);

		return result;
	}

}
