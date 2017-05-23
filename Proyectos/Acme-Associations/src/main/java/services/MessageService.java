
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Administrator;
import domain.Association;
import domain.Folder;
import domain.Loan;
import domain.Message;
import domain.Request;
import domain.User;
import forms.MessageBroadcast;

@Service
@Transactional
@EnableScheduling
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
	private UserService				userService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private FolderService			folderService;

	@Autowired
	private LoanService				loanService;


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

	public Message createBlank() {
		final Message result = new Message();
		result.setMoment(new Date(System.currentTimeMillis() - 1));

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

		sender = message.getSender();
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

	public Message broadcast(final MessageBroadcast messageBroad) {

		Message message;
		User principal;

		principal = this.userService.findByPrincipal();
		message = this.create();

		message.setTitle("BROAD: " + messageBroad.getAssociation().getName());
		message.setText(messageBroad.getText());
		message.setMoment(new Date(System.currentTimeMillis() - 1));
		message.setSender(principal);

		Collection<User> recipients;
		//		final Pageable page = new PageRequest(0, 100); //Second index is the size of the page
		recipients = this.userService.findAllByAssociation(messageBroad.getAssociation());

		for (final User c : recipients) {
			final Folder recipientFolder = this.folderService.findSystemFolder(c, "Received");
			message.setFolder(recipientFolder);
			message.setRecipient(c);
			this.messageRepository.save(message);
		}

		return message;
	}
	public Message sendAccepted(final Request request) {
		Message message;
		User user;
		Association association;

		user = request.getUser();
		association = request.getAssociation();

		final Administrator admin = this.adminService.findSystemAdministrator();
		message = this.create();
		message.setRecipient(user);
		message.setSender(admin);
		message.setText("You have been accepted in the association " + association.getName());
		message.setTitle("AUTO: Request warning");

		Actor recipient;
		Folder recipientFolder;

		recipient = message.getRecipient();

		recipientFolder = this.folderService.findSystemFolder(recipient, "Received");
		message.setMoment(new Date(System.currentTimeMillis() - 1));
		message.setFolder(recipientFolder);

		this.messageRepository.save(message);

		return message;
	}

	public Message sendDenied(final Request request) {
		Message message;
		User user;
		Association association;

		user = request.getUser();
		association = request.getAssociation();

		final Administrator admin = this.adminService.findSystemAdministrator();
		message = this.create();
		message.setRecipient(user);
		message.setSender(admin);
		message.setText("your request for the association " + association.getName() + " has been denied");
		message.setTitle("AUTO: Request warning");

		Actor recipient;
		Folder recipientFolder;

		recipient = message.getRecipient();

		recipientFolder = this.folderService.findSystemFolder(recipient, "Received");
		message.setMoment(new Date(System.currentTimeMillis() - 1));
		message.setFolder(recipientFolder);

		this.messageRepository.save(message);

		return message;
	}
	//	public void automaticMessage(final Event event) {
	//
	//		Message message;
	//		Collection<User> recipients;
	//
	//		message = this.create();
	//		final Pageable page = new PageRequest(0, 100); //Second index is the size of the page
	//		recipients = this.userService.findUseresRegisteredEvent(event.getId(), page);
	//
	//		final String text = "The event " + event.getTitle() + " in which you are registered has been edited or deleted \n" + "El evento " + event.getTitle() + " en el que está registrado ha sido modificado o borrado";
	//		final String subject = event.getTitle() + " Warn";
	//
	//		message.setSubject(subject);
	//		message.setText(text);
	//		message.setMoment(new Date(System.currentTimeMillis() - 1));
	//		while (!recipients.isEmpty()) {
	//			for (final User c : recipients) {
	//				final Folder recipientFolder = this.folderService.findSystemFolder(c, "Received");
	//				message.setFolder(recipientFolder);
	//				message.setRecipient(c);
	//				this.messageRepository.save(message);
	//
	//			}
	//			recipients = this.userService.findUseresRegisteredEvent(event.getId(), page.next());
	//		}
	//	}
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

	@Scheduled(cron = "0 0 0 * * *")
	public void sendMessageOverdueLoan() {

		List<Loan> loans;
		Administrator system;
		final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		system = this.adminService.findSystemAdministrator();

		final Pageable page = new PageRequest(0, 100); //Second index is the size of the page
		loans = this.loanService.findOverdueLoans(page);

		while (!loans.isEmpty()) {
			for (final Loan l : loans) {
				final Message m = this.createBlank();
				m.setRecipient(l.getBorrower());
				m.setSender(system);
				m.setTitle("Loan of an item overdue");
				m.setText("This is an autogenerated message: \n The item " + l.getItem().getName() + " with identifier " + l.getItem().getIdentifier() + " from the association " + l.getItem().getSection().getAssociation()
					+ " should have been delivered on " + formatter.format(l.getExpectedDate()));
				final Folder inbox = this.folderService.findSystemFolder(l.getBorrower(), "Received");
				m.setFolder(inbox);
				this.messageRepository.save(m);
			}
			loans = this.loanService.findOverdueLoans(page.next());
		}

	}
}
