
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import services.ItemService;
import services.RequestService;
import services.RolesService;
import domain.Item;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

	//Constructor

	public ItemController() {
		super();
	}


	//Service

	@Autowired
	private ItemService		associationService;

	@Autowired
	private RequestService	requestService;

	@Autowired
	private CommentService	commentService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private RolesService	rolesService;

	@Autowired
	private ItemService		itemService;


	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final String filter) {
		ModelAndView result;

		final Collection<Item> items = this.itemService.filterItems(filter);

		result = new ModelAndView("item/list");
		result.addObject("items", items);
		result.addObject("requestURI", "/item/filter.do");

		return result;
	}
}
