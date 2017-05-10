
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import domain.Actor;
import domain.Chirp;
import domain.Chorbi;
import domain.Event;
import domain.Folder;
import forms.ChirpBroadcast;

@Service
@Transactional
public class ChirpService {

	//Constructor

	public ChirpService() {
		super();
	}


	//Managed Repository

	@Autowired
	private ChirpRepository			messageRepository;

	//Auxiliary Services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ChorbiService			chorbiService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private FolderService			folderService;


	//CRUD

	public Chirp create() {
		final Chirp result = new Chirp();
		Actor sender;
		final Collection<String> attachments = new ArrayList<String>();

		sender = this.actorService.findByPrincipal();

		if (sender instanceof Chorbi) {
			final Chorbi senderChorbi = this.chorbiService.findByPrincipal();
			final Folder senderFolder = this.folderService.findSystemFolder(senderChorbi, "Sent");
			result.setFolder(senderFolder);
		}
		result.setMoment(new Date());
		result.setSender(sender);
		result.setAttachments(attachments);
		return result;
	}

	public Chirp findOne(final int messageId) {
		Chirp result;

		result = this.messageRepository.findOne(messageId);

		return result;
	}

	public Collection<Chirp> findAll() {
		return this.messageRepository.findAll();
	}

	public Collection<Chirp> findAllByFolder(final int folderId) {
		Collection<Chirp> result;
		this.folderService.checkPrincipal(folderId);
		result = this.messageRepository.findByFolderId(folderId);
		return result;
	}

	public Collection<Chirp> findAllByFolderWithNoCheck(final int folderId) {
		Collection<Chirp> result;
		result = this.messageRepository.findByFolderId(folderId);
		return result;
	}

	//	public Chirp save(Chirp message){
	//		Chirp result;
	//		folderService.checkPrincipal(message.getFolder());
	//		result = messageRepository.save(message);
	//		return result;
	//	}

	public void delete(final Chirp message) {

		this.checkPrincipal(message);

		this.messageRepository.delete(message);

	}

	//Business methods

	public Chirp send(final Chirp message) {

		Chorbi recipient;
		Folder recipientFolder;
		Folder senderFolder;
		Chorbi sender;

		sender = this.chorbiService.findByPrincipal();
		recipient = (Chorbi) message.getRecipient();

		recipientFolder = this.folderService.findSystemFolder(recipient, "Received");
		senderFolder = this.folderService.findSystemFolder(sender, "Sent");

		message.setMoment(new Date(System.currentTimeMillis() - 1));
		message.setFolder(senderFolder);

		this.messageRepository.save(message);

		message.setFolder(recipientFolder);

		this.messageRepository.save(message);

		return message;
	}

	public Collection<String> addAttachment(Collection<String> attachments, final String attachment) {

		if (attachments == null) {
			attachments = new ArrayList<String>();
			attachments.add(attachment);
		} else
			attachments.add(attachment);

		return attachments;

	}
	public Chirp move(final Chirp message, final Folder folder) {
		Chirp result;
		this.checkPrincipal(message);
		this.folderService.checkPrincipal(folder);
		message.setFolder(folder);
		result = this.messageRepository.save(message);
		return result;
	}

	public void flush() {
		this.messageRepository.flush();

	}

	public Chirp reply(final Chirp chirp) {
		final Chirp result;
		result = this.create();
		Collection<String> attachments;
		attachments = new ArrayList<String>(chirp.getAttachments());
		result.setAttachments(attachments);
		result.setSubject("Re: " + chirp.getSubject());
		result.setRecipient(chirp.getSender());

		return result;
	}

	public Chirp reSend(final Chirp chirp, final Chorbi chorbi) {

		Chirp result;
		result = this.create();
		final Collection<String> attachments = new ArrayList<String>(chirp.getAttachments());
		result.setAttachments(attachments);
		result.setSubject(chirp.getSubject());
		result.setText(chirp.getText());
		result.setRecipient(chorbi);
		this.send(result);

		return result;
	}

	public Chirp broadcast(final ChirpBroadcast chirp) {

		Chirp message;
		message = this.create();

		String subject = chirp.getSubject();
		subject = "BROAD: " + subject;

		message.setAttachments(chirp.getAttachments());
		message.setSubject(subject);
		message.setText(chirp.getText());
		message.setMoment(new Date(System.currentTimeMillis() - 1));

		Collection<Chorbi> recipients;
		final Pageable page = new PageRequest(0, 100); //Second index is the size of the page
		recipients = this.chorbiService.findChorbiesRegisteredEvent(chirp.getEvent().getId(), page);

		while (!recipients.isEmpty()) {

			for (final Chorbi c : recipients) {
				final Folder recipientFolder = this.folderService.findSystemFolder(c, "Received");
				message.setFolder(recipientFolder);
				message.setRecipient(c);
				this.messageRepository.save(message);
			}
			recipients = this.chorbiService.findChorbiesRegisteredEvent(chirp.getEvent().getId(), page.next());
		}

		return message;
	}

	public void automaticChirp(final Event event) {

		Chirp chirp;
		Collection<Chorbi> recipients;

		chirp = this.create();
		final Pageable page = new PageRequest(0, 100); //Second index is the size of the page
		recipients = this.chorbiService.findChorbiesRegisteredEvent(event.getId(), page);

		final String text = "The event " + event.getTitle() + " in which you are registered has been edited or deleted \n" + "El evento " + event.getTitle() + " en el que está registrado ha sido modificado o borrado";
		final String subject = event.getTitle() + " Warn";

		chirp.setSubject(subject);
		chirp.setText(text);
		chirp.setMoment(new Date(System.currentTimeMillis() - 1));
		while (!recipients.isEmpty()) {
			for (final Chorbi c : recipients) {
				final Folder recipientFolder = this.folderService.findSystemFolder(c, "Received");
				chirp.setFolder(recipientFolder);
				chirp.setRecipient(c);
				this.messageRepository.save(chirp);

			}
			recipients = this.chorbiService.findChorbiesRegisteredEvent(event.getId(), page.next());
		}
	}
	// Principal Checkers

	public void checkPrincipalSender(final Chirp message) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor.getUserAccount().equals(message.getSender()));
	}

	public void checkPrincipalRecipient(final Chirp message) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor.getUserAccount().equals(message.getRecipient()));
	}

	public void checkPrincipal(final Chirp message) {
		final Actor actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor.equals(message.getSender()) || actor.equals(message.getRecipient()));
	}

	public boolean checkAttachment(final String attachment) {

		boolean result = false;
		if (attachment.matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"))
			result = true;

		return result;
	}
}
