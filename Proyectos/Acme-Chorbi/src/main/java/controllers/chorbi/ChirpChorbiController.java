
package controllers.chorbi;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.ChorbiService;
import services.FolderService;
import controllers.AbstractController;
import domain.Chirp;
import domain.Chorbi;
import domain.Folder;
import forms.ChirpAttach;
import forms.ResendChirp;

@Controller
@RequestMapping("/chirp/chorbi")
public class ChirpChorbiController extends AbstractController {

	//Services

	@Autowired
	private ChirpService	messageService;

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ChorbiService	actorService;


	//Contructor

	public ChirpChorbiController() {
		super();
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int folderId) {

		ModelAndView result;
		Collection<Chirp> messages;
		Folder folder;
		ResendChirp resendChirp;
		Collection<Chorbi> chorbies;

		chorbies = this.actorService.findAll();
		resendChirp = new ResendChirp();

		folder = this.folderService.findOne(folderId);
		final String requestURI = "chirp/chorbi/list.do?folderId=" + folderId;

		try {
			messages = this.messageService.findAllByFolder(folderId);
			result = new ModelAndView("message/list");
			result.addObject("messages", messages);
			result.addObject("requestURI", requestURI);
			result.addObject("folder", folder);
			result.addObject("resendChirp", resendChirp);
			result.addObject("chorbies", chorbies);
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/folder/actor/list.do");
			result.addObject("errorChirp", "message.folder.wrong");

		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		final Chirp message = this.messageService.create();

		result = this.createEditModelAndView(message);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ChirpAttach chirpAttach, final BindingResult binding) {
		Chorbi principal;
		ModelAndView result;
		Chirp sent;
		Chirp chirp;

		chirp = this.messageService.create();
		chirp.setAttachments(chirpAttach.getAttachments());
		chirp.setRecipient(chirpAttach.getRecipient());
		chirp.setSubject(chirpAttach.getSubject());
		chirp.setText(chirpAttach.getText());

		if (binding.hasErrors())
			result = this.createEditModelAndView(chirpAttach);
		else
			try {

				sent = this.messageService.send(chirp);
				principal = this.actorService.findByPrincipal();
				result = new ModelAndView("redirect:/chirp/chorbi/list.do?folderId=" + this.folderService.findSystemFolder(principal, "Sent").getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(chirp, "message.commit.error", null);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "attach")
	public ModelAndView attach(final ChirpAttach chirpAttach) {
		ModelAndView result;

		Chirp message;
		String attachment;

		message = this.messageService.create();
		message.setAttachments(chirpAttach.getAttachments());
		message.setRecipient(chirpAttach.getRecipient());
		message.setSubject(chirpAttach.getSubject());
		message.setText(chirpAttach.getText());
		attachment = chirpAttach.getAttachment();

		final Collection<String> attachments = chirpAttach.getAttachments();

		if (this.messageService.checkAttachment(chirpAttach.getAttachment()))
			try {
				message.getAttachments().add(attachment);
				result = this.createEditModelAndView(message);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "message.commit.error", null);
			}
		else {

			result = this.createEditModelAndView(message);
			result.addObject("urlError", "chirp.attach.error");

		}

		return result;
	}
	//TODO Cuando lanza la excepción a dónde lo mando?
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {
		ModelAndView result;

		Chirp message;

		try {
			message = this.messageService.findOne(messageId);
			this.messageService.delete(message);
			result = new ModelAndView("redirect:/chirp/chorbi/list.do?folderId=" + message.getFolder().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("errorChirp", "message.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/resend", method = RequestMethod.POST, params = "save")
	public ModelAndView resend(@Valid final ResendChirp resendChirp, final BindingResult binding) {
		Chorbi principal;
		ModelAndView result;
		Chirp sent;
		final Chorbi recipient;

		if (binding.hasErrors())
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			try {
				recipient = this.actorService.findOne(resendChirp.getRecipientId());
				sent = this.messageService.findOne(resendChirp.getChirpId());
				sent = this.messageService.reSend(sent, recipient);
				principal = this.actorService.findByPrincipal();
				result = new ModelAndView("redirect:/chirp/chorbi/list.do?folderId=" + this.folderService.findSystemFolder(principal, "Sent").getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		return result;
	}
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam final int chirpId) {
		ModelAndView result;
		final Chirp chirp = this.messageService.findOne(chirpId);
		final Chirp reply = this.messageService.reply(chirp);

		result = this.createEditModelAndView(reply);

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Chirp message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ChirpAttach chirpAttach) {
		ModelAndView result;

		result = this.createEditModelAndView(null, null, chirpAttach);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Chirp message, final String errorMessage, final ChirpAttach ca) {
		ModelAndView result;
		Collection<Chorbi> actors;
		actors = this.actorService.findAll();
		if (ca == null) {
			final ChirpAttach chirpAttach = new ChirpAttach();
			chirpAttach.setAttachments(message.getAttachments());
			chirpAttach.setFolder(message.getFolder());
			chirpAttach.setMoment(message.getMoment());
			chirpAttach.setRecipient(message.getRecipient());
			chirpAttach.setSender(message.getSender());
			chirpAttach.setSubject(message.getSubject());
			chirpAttach.setText(message.getText());
			actors = this.actorService.findAll();

			result = new ModelAndView("message/edit");
			result.addObject("message", errorMessage);

			result.addObject("chirpAttach", chirpAttach);
		} else {

			result = new ModelAndView("message/edit");
			result.addObject("chirpAttach", ca);
		}
		result.addObject("actors", actors);
		return result;
	}
}
