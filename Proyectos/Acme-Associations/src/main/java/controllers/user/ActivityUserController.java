
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ActivityService;
import services.AssociationService;
import services.ItemService;
import services.RolesService;
import services.UserService;
import controllers.AbstractController;
import domain.Activity;
import domain.Association;
import domain.Item;
import domain.Roles;
import domain.User;
import forms.AddWinner;

@Controller
@RequestMapping("/activity/user")
public class ActivityUserController extends AbstractController {

	//Constructor

	public ActivityUserController() {
		super();
	}


	//Service

	@Autowired
	private ActivityService		activityService;

	@Autowired
	private AssociationService	associationService;

	@Autowired
	private UserService			userService;

	@Autowired
	private RolesService		roleService;

	@Autowired
	private ItemService			itemService;


	@RequestMapping(value = "/{association}/create", method = RequestMethod.GET)
	public ModelAndView create(@PathVariable final Association association) {
		ModelAndView result;

		Activity activity;

		activity = this.activityService.create(association);

		result = this.createEditModelAndView(activity);
		result.addObject("activity", activity);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Activity activity, final BindingResult binding) {
		ModelAndView result;

		final Association association = activity.getAssociation();
		Activity newActivity;

		if (binding.hasErrors())
			result = this.createEditModelAndView(activity);
		else
			try {
				newActivity = this.activityService.save(activity);
				result = new ModelAndView("redirect:/activity/" + association.getId() + "/" + newActivity.getId() + "/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(activity, oops.getMessage());
			}
		return result;
	}

	@RequestMapping(value = "/{association}/{activity}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable final Activity activity, @PathVariable final Association association, final RedirectAttributes redir) {
		ModelAndView result;
		try {
			this.roleService.checkManagerPrincipal(association);
			this.roleService.checkCollaboratorPrincipal(association);

			result = this.createEditModelAndView(activity);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", oops.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/addWinner", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final AddWinner addWinner, final BindingResult binding, final RedirectAttributes redir) {
		ModelAndView result;

		try {
			this.roleService.checkCollaboratorPrincipal(addWinner.getActivity().getAssociation());
			Activity activity = addWinner.getActivity();
			activity.setWinner(addWinner.getUser());
			activity = this.activityService.save(activity);
			result = new ModelAndView("redirect:/activity/" + activity.getAssociation().getId() + "/" + activity.getId() + "/display.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("flashMessage", oops.getMessage());
		}
		return result;
	}
	@RequestMapping(value = "/{association}/{activity}/register", method = RequestMethod.GET)
	public ModelAndView register(@PathVariable final Association association, final Activity activity, final RedirectAttributes redir) {
		ModelAndView result;

		if (activity.getPublicActivity())
			try {
				final Roles role = this.roleService.findRolesByPrincipalAssociation(association);

				Assert.isTrue(role.getType() == "MANAGER" || role.getType() == "COLLABORATOR" || role.getType() == "ASSOCIATE");

				this.activityService.addParticipant(this.userService.findByPrincipal(), activity);
				result = new ModelAndView("redirect:/welcome/index.do");
				result.addObject("flashMessage", "activity.added");
			} catch (final Exception e) {

				result = new ModelAndView("redirect:/welcome/index.do");
				redir.addFlashAttribute("errorMessage", e.getMessage());
			}
		else
			try {
				this.activityService.addParticipant(this.userService.findByPrincipal(), activity);
				result = new ModelAndView("redirect:/welcome/index.do");
				result.addObject("flashMessage", "activity.added");
			} catch (final Exception e) {

				result = new ModelAndView("redirect:/welcome/index.do");
				redir.addFlashAttribute("errorMessage", e.getMessage());
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Activity activity) {
		ModelAndView result;

		result = this.createEditModelAndView(activity, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Activity activity, final String message) {
		ModelAndView result;

		final String requestURI = "activity/user/edit.do";

		Collection<Item> item;
		Collection<User> winner;

		item = this.itemService.findAllByAssociation(activity.getAssociation());
		winner = activity.getAttendants();

		result = new ModelAndView("activity/edit");
		result.addObject("activity", activity);
		result.addObject("item", item);
		result.addObject("winner", winner);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
