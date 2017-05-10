
package controllers.chorbi;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import controllers.AbstractController;
import domain.Event;

@Controller
@RequestMapping("/event/chorbi")
public class EventChorbiController extends AbstractController {

	//Services

	@Autowired
	private EventService	eventService;


	//Constructor

	public EventChorbiController() {
		super();
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam final int eventId) {
		ModelAndView result;
		final Event event = this.eventService.findOne(eventId);

		this.eventService.register(event);
		result = new ModelAndView("redirect:/event/chorbi/list.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Event> events;

		events = this.eventService.findAllByPrincipalChorbi();

		result = new ModelAndView("event/list");
		result.addObject("requestURI", "event/chorbi/list.do");
		result.addObject("events", events);
		result.addObject("errorMessage", errorMessage);

		return result;
	}

	// Ancillary methods

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	//Needs further testing

}
