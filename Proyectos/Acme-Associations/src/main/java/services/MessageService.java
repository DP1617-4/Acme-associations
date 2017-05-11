
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//Constructor

	public MessageService() {
		super();
	}


	//Managed Repository

	@Autowired
	private MessageRepository		messageRepository;

	//Auxiliary Services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ActorService			userService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private FolderService			folderService;


	//CRUD

	public Message create() {
		final Message result = new Message();
		Actor sender;

		sender = this.actorService.findByPrincipal();

		final Folder senderFolder = this.folderService.findSystemFolder(sender, "Sent");
		result.setFolder(senderFolder);

		result.setMoment(new Date());
		result.setSender(sender);
		return result;
	}

	public Message findOne(final int messageId) {
		Message result;

		result = this.messageRepository.findOne(messageId);

		return result;
	}

	public Collection<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Collection<Message> findAllByFolder(final int folderId) {
		Collection<Message> result;
		this.folderService.checkPrincipal(folderId);
		result = this.messageRepository.findByFolderId(folderId);
		return result;
	}

	public Collection<Message> findAllByFolderWithNoCheck(final int folderId) {
		Collection<Message> result;
		result = this.messageRepository.findByFolderId(folderId);
		return result;
	}

	//	public Message save(Message message){
	//		Message result;
	//		folderService.checkPrincipal(message.getFolder());
	//		result = messageRepository.save(message);
	//		return result;
	//	}

	public void delete(final Message message) {

		this.checkPrincipal(message);

		this.messageRepository.delete(message);

	}

	//Business methods

	public Message send(final Message message) {

		Actor recipient;
		Folder recipientFolder;
		Folder senderFolder;
		Actor sender;

		sender = this.userService.findByPrincipal();
		recipient = message.getRecipient();

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
	public Message move(final Message message, final Folder folder) {
		Message result;
		this.checkPrincipal(message);
		this.folderService.checkPrincipal(folder);
		message.setFolder(folder);
		result = this.messageRepository.save(message);
		return result;
	}

	public void flush() {
		this.messageRepository.flush();

	}

	public Message reply(final Message message) {
		final Message result;
		result = this.create();
		result.setTitle("Re: " + message.getTitle());
		result.setRecipient(message.getSender());

		return result;
	}

	public Message reSend(final Message message, final Actor user) {

		Message result;
		result = this.create();
		result.setTitle(message.getTitle());
		result.setText(message.getText());
		result.setRecipient(user);
		this.send(result);

		return result;
	}

	public Message broadcast(final MessageBroadcast message) {

		Message message;
		message = this.create();

		String subject = message.getSubject();
		subject = "BROAD: " + subject;

		message.setAttachments(message.getAttachments());
		message.setSubject(subject);
		message.setText(message.getText());
		message.setMoment(new Date(System.currentTimeMillis() - 1));

		Collection<User> recipients;
		final Pageable page = new PageRequest(0, 100); //Second index is the size of the page
		recipients = this.userService.findUseresRegisteredEvent(message.getEvent().getId(), page);

		while (!recipients.isEmpty()) {

			for (final User c : recipients) {
				final Folder recipientFolder = this.folderService.findSystemFolder(c, "Received");
				message.setFolder(recipientFolder);
				message.setRecipient(c);
				this.messageRepository.save(message);
			}
			recipients = this.userService.findUseresRegisteredEvent(message.getEvent().getId(), page.next());
		}

		return message;
	}

	public void automaticMessage(final Event event) {

		Message message;
		Collection<User> recipients;

		message = this.create();
		final Pageable page = new PageRequest(0, 100); //Second index is the size of the page
		recipients = this.userService.findUseresRegisteredEvent(event.getId(), page);

		final String text = "The event " + event.getTitle() + " in which you are registered has been edited or deleted \n" + "El evento " + event.getTitle() + " en el que está registrado ha sido modificado o borrado";
		final String subject = event.getTitle() + " Warn";

		message.setSubject(subject);
		message.setText(text);
		message.setMoment(new Date(System.currentTimeMillis() - 1));
		while (!recipients.isEmpty()) {
			for (final User c : recipients) {
				final Folder recipientFolder = this.folderService.findSystemFolder(c, "Received");
				message.setFolder(recipientFolder);
				message.setRecipient(c);
				this.messageRepository.save(message);

			}
			recipients = this.userService.findUseresRegisteredEvent(event.getId(), page.next());
		}
	}
	// Principal Checkers

	public void checkPrincipalSender(final Message message) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor.getUserAccount().equals(message.getSender()));
	}

	public void checkPrincipalRecipient(final Message message) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor.getUserAccount().equals(message.getRecipient()));
	}

	public void checkPrincipal(final Message message) {
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
