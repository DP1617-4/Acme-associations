
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ActorService;
import services.ItemService;
import services.RolesService;
import services.SectionService;
import services.UserService;
import controllers.AbstractController;
import domain.Actor;
import domain.Association;
import domain.Item;
import domain.Roles;
import domain.Section;
import domain.User;
import forms.ChangeCondition;

@Controller
@RequestMapping("/item/user")
public class ItemUserController extends AbstractController {

	public ItemUserController() {
		super();
	}


	@Autowired
	private RolesService	rolesService;

	@Autowired
	private ItemService		itemService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private UserService		userService;

	@Autowired
	private SectionService	sectionService;


	@RequestMapping(value = "/{association}/{section}/create", method = RequestMethod.GET)
	public ModelAndView create(@PathVariable final Association association, @PathVariable final Section section, final RedirectAttributes redir) {
		ModelAndView result;
		try {
			this.sectionService.checkResponsiblePrincipal(section.getId());
			final Item item = this.itemService.create(section);
			result = this.createEditModelAndView(item);
		} catch (final Exception e) {

			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Item item, final BindingResult binding) {
		ModelAndView result;

		final Association association = item.getSection().getAssociation();
		Item newItem;

		if (binding.hasErrors())
			result = this.createEditModelAndView(item);
		else
			try {
				newItem = this.itemService.save(item);
				result = new ModelAndView("redirect:/item/user/" + association.getId() + "/display.do?itemId=" + newItem.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(item, oops.getMessage());
			}
		return result;
	}

	@RequestMapping(value = "/{association}/list", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable final Association association) {
		final ModelAndView result;

		final Collection<Item> items = this.itemService.findAllByAssociation(association);

		Roles roles = null;
		String role = null;

		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final Actor actPrincipal = this.actorService.findByPrincipal();

		if (principal != "anonymousUser")
			if (actPrincipal instanceof User)
				roles = this.rolesService.findRolesByPrincipalAssociation(association);

		if (roles != null)
			role = roles.getType();

		result = new ModelAndView("item/list");
		result.addObject("items", items);
		result.addObject("requestURI", "/item/user/" + association.getId() + "/list.do");
		result.addObject("association", association);
		result.addObject("role", role);

		return result;
	}

	@RequestMapping(value = "/{association}/{section}/list", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable final Association association, @PathVariable final Section section) {
		final ModelAndView result;

		final Collection<Item> items = this.itemService.findAllBySection(section);

		Roles roles = null;
		String role = null;

		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final Actor actPrincipal = this.actorService.findByPrincipal();

		if (principal != "anonymousUser")
			if (actPrincipal instanceof User)
				roles = this.rolesService.findRolesByPrincipalAssociation(association);

		if (roles != null)
			role = roles.getType();

		result = new ModelAndView("item/list");
		result.addObject("items", items);
		result.addObject("requestURI", "/item/user/" + association.getId() + "/list.do");
		result.addObject("association", association);
		result.addObject("role", role);

		return result;
	}

	@RequestMapping(value = "/{association}/display", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable final Association association, @RequestParam final int itemId) {
		final ModelAndView result;

		Item item;
		ChangeCondition changeCondition;

		final Actor actPrincipal = this.actorService.findByPrincipal();

		item = this.itemService.findOne(itemId);
		changeCondition = new ChangeCondition();

		changeCondition.setItem(item);

		Roles roles = null;
		String role = null;

		boolean isCharge = false;
		if (item.getSection().getUser().equals(actPrincipal))
			isCharge = true;

		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal != "anonymousUser")
			if (actPrincipal instanceof User)
				roles = this.rolesService.findRolesByPrincipalAssociation(association);

		if (roles != null)
			role = roles.getType();

		result = new ModelAndView("item/display");
		result.addObject("item", item);
		result.addObject("requestURI", "/item/user/" + association.getId() + "/display.do?itemId=" + itemId);
		result.addObject("association", association);
		result.addObject("changeCondition", changeCondition);
		result.addObject("role", role);
		result.addObject("isCharge", isCharge);

		return result;
	}

	@RequestMapping(value = "/{association}/{item}/changeCondition", method = RequestMethod.POST, params = "save")
	public ModelAndView changeCondition(final ChangeCondition changeCondition, @PathVariable final Association association, @PathVariable final Item item, final BindingResult binding, final RedirectAttributes redir) {
		final ModelAndView result;

		this.itemService.changeCondition(item, changeCondition.getCondition());
		result = new ModelAndView("redirect:/item/user/" + association.getId() + "/display.do?itemId=" + item.getId());

		return result;
	}

	protected ModelAndView createEditModelAndView(final Item item) {
		ModelAndView result;

		result = this.createEditModelAndView(item, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Item item, final String message) {
		ModelAndView result;

		final String requestURI = "item/user/edit.do";

		result = new ModelAndView("item/edit");
		result.addObject("item", item);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
