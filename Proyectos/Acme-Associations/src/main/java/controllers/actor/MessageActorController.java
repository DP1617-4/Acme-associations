
package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ActorService;
import services.FolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Association;
import domain.Folder;
import domain.Message;
import forms.MessageBroadcast;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {

	//Services

	@Autowired
	private MessageService	messageService;

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


	//Contructor

	public MessageActorController() {
		super();
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int folderId) {

		ModelAndView result;
		Collection<Message> messages;
		Folder folder;
		Collection<Actor> actors;

		actors = this.actorService.findAll();

		folder = this.folderService.findOne(folderId);
		final String requestURI = "message/actor/list.do?folderId=" + folderId;

		try {
			messages = this.messageService.findAllByFolder(folderId);
			result = new ModelAndView("message/list");
			result.addObject("messages", messages);
			result.addObject("requestURI", requestURI);
			result.addObject("folder", folder);
			result.addObject("actors", actors);
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/folder/actor/list.do");
			result.addObject("errorMessage", "message.folder.wrong");

		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		final Message message = this.messageService.create();

		result = this.createEditModelAndView(message);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Message message, final BindingResult binding) {
		Actor principal;
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {

				this.messageService.send(message);
				principal = this.actorService.findByPrincipal();
				result = new ModelAndView("redirect:/message/actor/list.do?folderId=" + this.folderService.findSystemFolder(principal, "Sent").getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "message.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/broadcast", method = RequestMethod.POST, params = "broadcast")
	public ModelAndView broadcast(@Valid final MessageBroadcast messageBroad, final BindingResult binding, final RedirectAttributes redir) {
		ModelAndView result;
		Association association;

		association = messageBroad.getAssociation();

		if (binding.hasErrors())
			result = new ModelAndView("redirect:/association/" + association.getId() + "/display.do");
		else
			try {

				this.messageService.broadcast(messageBroad);
				result = new ModelAndView("redirect:/association/" + association.getId() + "/display.do");
				redir.addFlashAttribute("flashMessage", "message.correct");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/association/" + association.getId() + "/display.do");
				redir.addFlashAttribute("broadError", oops.getMessage());

			}

		return result;
	}
	//TODO Cuando lanza la excepción a dónde lo mando?
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {
		ModelAndView result;

		Message message;

		try {
			message = this.messageService.findOne(messageId);
			this.messageService.delete(message);
			result = new ModelAndView("redirect:/message/actor/list.do?folderId=" + message.getFolder().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("errorMessage", "message.commit.error");
		}

		return result;
	}

	//	@RequestMapping(value = "/resend", method = RequestMethod.POST, params = "save")
	//	public ModelAndView resend(@Valid final ResendMessage resendMessage, final BindingResult binding) {
	//		Actor principal;
	//		ModelAndView result;
	//		Message sent;
	//		final Actor recipient;
	//
	//		if (binding.hasErrors())
	//			result = new ModelAndView("redirect:/welcome/index.do");
	//		else
	//			try {
	//				recipient = this.actorService.findOne(resendMessage.getRecipientId());
	//				sent = this.messageService.findOne(resendMessage.getMessageId());
	//				sent = this.messageService.reSend(sent, recipient);
	//				principal = this.actorService.findByPrincipal();
	//				result = new ModelAndView("redirect:/message/actor/list.do?folderId=" + this.folderService.findSystemFolder(principal, "Sent").getId());
	//			} catch (final Throwable oops) {
	//				result = new ModelAndView("redirect:/welcome/index.do");
	//			}
	//
	//		return result;
	//	}

	//	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	//	public ModelAndView reply(@RequestParam final int messageId) {
	//		ModelAndView result;
	//		final Message message = this.messageService.findOne(messageId);
	//		final Message reply = this.messageService.reply(message);
	//
	//		result = this.createEditModelAndView(reply);
	//
	//		return result;
	//
	//	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message, final String errorMessage) {

		ModelAndView result;
		Collection<Actor> actors;

		actors = this.actorService.findAll();

		result = new ModelAndView("message/edit");

		result.addObject("errorMessage", errorMessage);
		result.addObject("actors", actors);
		result.addObject("message", message);

		return result;

	}
}
