
package controllers.manager;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.ChorbiService;
import services.EventService;
import services.FolderService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Chorbi;
import domain.Event;
import domain.Manager;
import forms.ChirpBroadcast;

@Controller
@RequestMapping("/chirp/_manager")
public class ChirpManagerController extends AbstractController {

	//Services

	@Autowired
	private ChirpService	messageService;

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ChorbiService	actorService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private EventService	eventService;


	//Contructor

	public ChirpManagerController() {
		super();
	}

	//Listing

	@RequestMapping(value = "/broadcast", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		final ChirpBroadcast chirp;

		chirp = new ChirpBroadcast();

		result = this.createEditModelAndView(chirp);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ChirpBroadcast chirpBroadcast, final BindingResult binding) {
		final Manager principal;
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(chirpBroadcast);
		else
			try {
				this.messageService.broadcast(chirpBroadcast);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(chirpBroadcast, "message.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "attach")
	public ModelAndView attach(final ChirpBroadcast chirpBroadcast) {
		ModelAndView result;

		if (this.messageService.checkAttachment(chirpBroadcast.getAttachment()))
			try {
				chirpBroadcast.getAttachments().add(chirpBroadcast.getAttachment());
				result = this.createEditModelAndView(chirpBroadcast);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(chirpBroadcast, "message.commit.error");
			}
		else {

			result = this.createEditModelAndView(chirpBroadcast);
			result.addObject("urlError", "chirp.attach.error");

		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final ChirpBroadcast chirp) {
		ModelAndView result;

		result = this.createEditModelAndView(chirp, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ChirpBroadcast chirp, final String errorMessage) {
		ModelAndView result;
		final Collection<Chorbi> actors;
		Collection<Event> events;

		events = this.eventService.findAllByPrincipal();
		result = new ModelAndView("message/broadcast");
		result.addObject("chirpBroadcast", chirp);
		result.addObject("message", errorMessage);
		result.addObject("events", events);

		return result;
	}
}
