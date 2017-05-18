
package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RolesService;
import controllers.AbstractController;
import domain.Association;
import domain.Item;

@Controller
@RequestMapping("/item/user")
public class ItemUserController extends AbstractController {

	public ItemUserController() {
		super();
	}


	@Autowired
	private RolesService	rolesService;

	


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Item item = this.itemService.create();
		result = this.createEditModelAndView(item);
		return result;
	}

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
	public ModelAndView list(@PathVariable final Association association) {
		final ModelAndView result;

		final Collection<Item> items = this.itemService.findAllByAssociation(association);

		result = new ModelAndView("item/list");
		result.addObject("items", items);
		result.addObject("requestURI", "/item/user/" + association.getId() + "/list.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final Item item) {
		ModelAndView result;

		result = this.createEditModelAndView(item, null);

		return result;
	}

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
