
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
import domain.Activity;
import domain.Association;
import domain.Roles;
import domain.User;

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
	private ActorService	actorService;


	@RequestMapping(value = "/{activity}/display", method = RequestMethod.GET)
	public ModelAndView display(@PathVariable final Activity activity, @PathVariable final Association association) {
		ModelAndView result;
		Collection<User> users;
		users = activity.getAttendants();

		final Roles roles = null;

		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		result = new ModelAndView("association/display");
		result.addObject("activity", activity);
		result.addObject("users", users);
		result.addObject("requestURI", "/activity/" + association.getId() + "/display.do");
		return result;
	}
	@RequestMapping(value = "/{association}/list", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable final Association association) {
		ModelAndView result;

		final Collection<Activity> activities = this.activityService.findAllByAssociation(association.getId());

		result = new ModelAndView("association/list");
		result.addObject("activities", activities);
		result.addObject("requestURI", "/activity/" + association.getId() + "/list.do");

		return result;
	}

}
