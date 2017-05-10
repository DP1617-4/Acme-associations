
package controllers.manager;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.EventService;
import services.ManagerService;
import controllers.AbstractController;
import domain.CreditCard;
import domain.Event;
import domain.Manager;

@Controller
@RequestMapping("/event/_manager")
public class EventManagerController extends AbstractController {

	//Services

	@Autowired
	private EventService		eventService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private CreditCardService	creditCardService;


	//Constructor

	public EventManagerController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Event event;
		event = this.eventService.create();
		result = this.createEditModelAndView(event);
		final Manager manager = this.managerService.findByPrincipal();
		this.managerService.updateFee(manager);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int eventId) {
		ModelAndView result;
		Event event;

		event = this.eventService.findOne(eventId);
		result = this.createEditModelAndView(event);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Event> events;

		events = this.eventService.findAllByPrincipal();

		result = new ModelAndView("event/list");
		result.addObject("requestURI", "event/_manager/list.do");
		result.addObject("events", events);
		result.addObject("errorMessage", errorMessage);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Event event, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(event);
		else
			try {
				final CreditCard creditCard = this.creditCardService.findByPrincipal();
				if (creditCard != null) {
					if (this.creditCardService.checkCCNumber(creditCard.getCreditCardNumber()) && this.creditCardService.expirationDate(creditCard)) {
						event = this.eventService.save(event);
						result = new ModelAndView("redirect:/event/_manager/list.do");
					} else
						result = new ModelAndView("redirect:/creditCard/_manager/edit.do");
				} else
					result = new ModelAndView("redirect:/creditCard/_manager/edit.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(event, "manager.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int eventId) {
		ModelAndView result;
		Event event;

		event = this.eventService.findOne(eventId);
		this.eventService.delete(event);

		result = new ModelAndView("redirect:/event/_manager/list.do");

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Event event) {
		ModelAndView result;

		result = this.createEditModelAndView(event, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Event event, final String message) {
		ModelAndView result;

		final String requestURI = "event/_manager/edit.do";

		result = new ModelAndView("event/edit");
		result.addObject("event", event);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	//Needs further testing

}
