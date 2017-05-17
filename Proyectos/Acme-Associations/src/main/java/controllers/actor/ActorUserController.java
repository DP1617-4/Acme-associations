
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.MessageService;
import services.UserService;
import controllers.AbstractController;
import domain.Actor;
import domain.Folder;
import domain.Message;
import domain.User;

@Controller
@RequestMapping("/actor/user")
public class ActorUserController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public ActorUserController() {
		super();
	}


	@Autowired
	private ActorService	actorService;

	@Autowired
	private UserService		userService;

	@Autowired
	private MessageService	messageService;

	@Autowired
	private FolderService	folderService;


	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/{actor}/display", method = RequestMethod.GET)
	public ModelAndView display(@PathVariable final Actor actor) {
		ModelAndView result;
		Folder folder;
		folder = this.folderService.findSystemFolder(actor, "Received");
		final Collection<Message> messages;
		messages = this.messageService.findAllByFolder(folder.getId());

		result = new ModelAndView("actor/display");
		result.addObject("actor", actor);
		result.addObject("messages", messages);

		return result;

	}

	@RequestMapping(value = "/displayOwn", method = RequestMethod.GET)
	public ModelAndView displayOwn() {
		ModelAndView result;
		Folder folder;
		final User actor = this.userService.findByPrincipal();
		folder = this.folderService.findSystemFolder(actor, "Received");
		final Collection<Message> messages;
		messages = this.messageService.findAllByFolder(folder.getId());

		result = new ModelAndView("actor/display");
		result.addObject("actor", actor);
		result.addObject("messages", messages);

		return result;

	}
	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Actor actor, final String message) {
		ModelAndView result;

		final String requestURI = "actor/user/edit.do";

		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
