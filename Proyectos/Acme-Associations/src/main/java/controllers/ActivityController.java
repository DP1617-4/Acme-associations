
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.RolesService;
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
	private RolesService	roleService;


	@RequestMapping(value = "/{association}/{activity}/display", method = RequestMethod.GET)
	public ModelAndView display(@PathVariable final Activity activity, @PathVariable final Association association) {
		ModelAndView result;
		Collection<User> users;
		users = activity.getAttendants();

		result = new ModelAndView("activity/display");
		result.addObject("activity", activity);
		result.addObject("users", users);
		result.addObject("association", association);
		result.addObject("requestURI", "activity/" + association.getId() + "/" + activity.getId() + "/display.do");
		return result;
	}
	@RequestMapping(value = "/{association}/list", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable final Association association) {
		ModelAndView result;

		final Roles roles = this.roleService.findRolesByPrincipalAssociation(association);
		String role = null;

		final Collection<Activity> activities = this.activityService.findAllByAssociation(association.getId());

		if (roles != null)
			role = roles.getType();

		result = new ModelAndView("activity/list");
		result.addObject("association", association);
		result.addObject("activities", activities);
		result.addObject("requestURI", "activity/" + association.getId() + "/list.do");
		result.addObject("role", role);

		return result;
	}
}
