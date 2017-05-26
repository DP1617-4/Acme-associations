
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.ActorService;
import services.RolesService;
import domain.Activity;
import domain.Actor;
import domain.Association;
import domain.Roles;
import domain.User;
import forms.AddWinner;

@Controller
@RequestMapping("/activity")
public class ActivityController extends AbstractController {

	//Constructor

	public ActivityController() {
		super();
	}


	//Service

	@Autowired
	private ActivityService	activityService;

	@Autowired
	private RolesService	roleService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/{association}/{activity}/display", method = RequestMethod.GET)
	public ModelAndView display(@PathVariable final Activity activity, @PathVariable final Association association) {
		ModelAndView result;
		Collection<User> users;
		users = activity.getAttendants();
		final AddWinner addWinner = new AddWinner();
		addWinner.setActivity(activity);

		Roles roles = null;
		String role = null;

		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		result = new ModelAndView("welcome/index");
		if (principal != "anonymousUser") {
			final Actor actPrincipal = this.actorService.findByPrincipal();
			if (actPrincipal instanceof User)
				roles = this.roleService.findRolesByPrincipalAssociation(association);
		}

		if (roles != null)
			role = roles.getType();

		result = new ModelAndView("activity/display");
		result.addObject("activity", activity);
		result.addObject("users", users);
		result.addObject("association", association);
		result.addObject("role", role);
		result.addObject("addWinner", addWinner);
		result.addObject("requestURI", "activity/" + association.getId() + "/" + activity.getId() + "/display.do");
		return result;
	}
	@RequestMapping(value = "/{association}/list", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable final Association association) {
		ModelAndView result;

		Roles roles = null;
		String role = null;

		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		result = new ModelAndView("welcome/index");

		final Collection<Activity> activities = this.activityService.findAllByAssociation(association.getId());

		result = new ModelAndView("activity/list");

		if (principal != "anonymousUser") {
			final Actor actPrincipal = this.actorService.findByPrincipal();
			if (actPrincipal instanceof User)
				roles = this.roleService.findRolesByPrincipalAssociation(association);
			result.addObject("actPrincipal", actPrincipal);
		}

		if (roles != null)
			role = roles.getType();

		result.addObject("association", association);
		result.addObject("activities", activities);
		result.addObject("requestURI", "activity/" + association.getId() + "/list.do");
		result.addObject("role", role);
		result.addObject("roles", roles);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;

		final Roles roles = null;
		final String role = null;

		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		result = new ModelAndView("welcome/index");

		final Collection<Activity> activities = this.activityService.findAllNotFinished();

		result = new ModelAndView("activity/list");
		result.addObject("activities", activities);
		result.addObject("requestURI", "activity/list.do");
		result.addObject("role", role);
		result.addObject("roles", roles);
		if (principal != "anonymousUser") {
			final Actor actPrincipal = this.actorService.findByPrincipal();
			result.addObject("actPrincipal", actPrincipal);
		}

		return result;
	}
}
