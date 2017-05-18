
package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import services.RolesService;
import services.UserService;
import controllers.AbstractController;
import domain.Item;
import domain.Roles;

@Controller
@RequestMapping("/item/user")
public class ItemUserController extends AbstractController {

	public ItemUserController() {
		super();
	}


	@Autowired
	private ItemService		itemService;

	@Autowired
	private RolesService	rolesService;

	@Autowired
	private UserService		userService;


	//	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//	public ModelAndView create() {
	//		ModelAndView result;
	//		final Item newItem = this.itemService.create();
	//		result = this.createEditModelAndView(newItem);
	//		return result;
	//	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(@Valid Item newItem, final BindingResult binding) {
	//		ModelAndView result;
	//
	//		if (binding.hasErrors())
	//			result = this.createEditModelAndView(newItem);
	//		else
	//			try {
	//				newItem = this.itemService.save(newItem);
	//				result = new ModelAndView("redirect:/item/" + newItem.getId() + "/display.do");
	//			} catch (final Throwable oops) {
	//				result = this.createEditModelAndView(newItem, "item.commit.error");
	//			}
	//		return result;
	//	}

	@RequestMapping(value = "/{association}/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Collection<Roles> roles = this.rolesService.findAllByPrincipal();

		result = new ModelAndView("item/listOwn");
		result.addObject("roles", roles);
		result.addObject("requestURI", "/user/item/listOwn.do");

		return result;
	}
	//En esta debería haber un mensaje de confirmación al acceder al enlace

	protected ModelAndView createEditModelAndView(final Item item, final String message) {
		ModelAndView result;

		final String requestURI = "user/item/edit.do";

		result = new ModelAndView("item/edit");
		result.addObject("item", item);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
