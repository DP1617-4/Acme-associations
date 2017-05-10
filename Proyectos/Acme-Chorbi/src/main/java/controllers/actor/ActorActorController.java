
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Chorbi;
import domain.Manager;

@Controller
@RequestMapping("/actor/actor")
public class ActorActorController extends AbstractController {

	//Services

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ActorActorController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") final int actorId) {

		ModelAndView result;
		Actor actor;

		result = new ModelAndView("redirect:/welcome/index.do");

		if (actorId == 0)
			actor = this.actorService.findByPrincipal();
		else
			actor = this.actorService.findOne(actorId);

		if (actor instanceof Chorbi)
			result = new ModelAndView("redirect:/chorbi/actor/display.do?chorbiId=" + actor.getId());
		else if (actor instanceof Administrator)
			result = new ModelAndView("redirect:/folder/actor/list.do");
		else if (actor instanceof Manager)
			result = new ModelAndView("redirect:/_manager/display.do?managerId=" + actor.getId());
		return result;
	}
}
