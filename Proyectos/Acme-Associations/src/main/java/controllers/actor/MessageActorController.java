
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

import services.ActorService;
import services.FolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Folder;
import domain.Message;

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
		Message sent;
		Message message;

		message = this.messageService.create();
		message.setAttachments(messageAttach.getAttachments());
		message.setRecipient(messageAttach.getRecipient());
		message.setSubject(messageAttach.getSubject());
		message.setText(messageAttach.getText());

		if (binding.hasErrors())
			result = this.createEditModelAndView(messageAttach);
		else
			try {

				sent = this.messageService.send(message);
				principal = this.actorService.findByPrincipal();
				result = new ModelAndView("redirect:/message/actor/list.do?folderId=" + this.folderService.findSystemFolder(principal, "Sent").getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "message.commit.error", null);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "attach")
	public ModelAndView attach(final MessageAttach messageAttach) {
		ModelAndView result;

		Message message;
		String attachment;

		message = this.messageService.create();
		message.setAttachments(messageAttach.getAttachments());
		message.setRecipient(messageAttach.getRecipient());
		message.setSubject(messageAttach.getSubject());
		message.setText(messageAttach.getText());
		attachment = messageAttach.getAttachment();

		final Collection<String> attachments = messageAttach.getAttachments();

		if (this.messageService.checkAttachment(messageAttach.getAttachment()))
			try {
				message.getAttachments().add(attachment);
				result = this.createEditModelAndView(message);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "message.commit.error", null);
			}
		else {

			result = this.createEditModelAndView(message);
			result.addObject("urlError", "message.attach.error");

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

	@RequestMapping(value = "/resend", method = RequestMethod.POST, params = "save")
	public ModelAndView resend(@Valid final ResendMessage resendMessage, final BindingResult binding) {
		Actor principal;
		ModelAndView result;
		Message sent;
		final Actor recipient;

		if (binding.hasErrors())
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			try {
				recipient = this.actorService.findOne(resendMessage.getRecipientId());
				sent = this.messageService.findOne(resendMessage.getMessageId());
				sent = this.messageService.reSend(sent, recipient);
				principal = this.actorService.findByPrincipal();
				result = new ModelAndView("redirect:/message/actor/list.do?folderId=" + this.folderService.findSystemFolder(principal, "Sent").getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		return result;
	}
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam final int messageId) {
		ModelAndView result;
		final Message message = this.messageService.findOne(messageId);
		final Message reply = this.messageService.reply(message);

		result = this.createEditModelAndView(reply);

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageAttach messageAttach) {
		ModelAndView result;

		result = this.createEditModelAndView(null, null, messageAttach);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message, final String errorMessage, final MessageAttach ca) {
		ModelAndView result;
		Collection<Actor> actors;
		actors = this.actorService.findAll();
		if (ca == null) {
			final MessageAttach messageAttach = new MessageAttach();
			messageAttach.setAttachments(message.getAttachments());
			messageAttach.setFolder(message.getFolder());
			messageAttach.setMoment(message.getMoment());
			messageAttach.setRecipient(message.getRecipient());
			messageAttach.setSender(message.getSender());
			messageAttach.setSubject(message.getSubject());
			messageAttach.setText(message.getText());
			actors = this.actorService.findAll();

			result = new ModelAndView("message/edit");
			result.addObject("message", errorMessage);

			result.addObject("messageAttach", messageAttach);
		} else {

			result = new ModelAndView("message/edit");
			result.addObject("messageAttach", ca);
		}
		result.addObject("actors", actors);
		return result;
	}
}
