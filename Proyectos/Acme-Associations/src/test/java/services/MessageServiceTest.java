
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Folder;
import domain.Message;
import forms.MessageBroadcast;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private MessageService		messageService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;

	@Autowired
	private AssociationService	associationService;


	// Tests ---------------------------------------------------------------
	//An actor who is authenticated must be able to:
	//	o Exchange messages with other actors.
	//	o Erase his or her messages, which requires previous confirmation
	@Test
	public void driverCreation() {

		final Object testingData[][] = {
			{		// Creación correcta de un Mensaje: Un adjunto
				"user1", "correcto", "correcto", null
			}, {		// Creación correcta de un Mensaje: Sin adjuntos;
				"user1", "correcto", "correcto", null
			}, {		// Creación correcta de un Mensaje: Muchos adjuntos;
				"user1", "correcto", "correcto", null
			}, {	// Creación errónea de un Mensaje: title vacío.
				"user1", "", "incorrecto", ConstraintViolationException.class
			}, {	// Creación errónea de un Mensaje: title nulo.
				"user1", null, "incorrecto", ConstraintViolationException.class
			}, {	// Creación errónea de un Mensaje: text vacío.
				"user1", "incorrecto", "", ConstraintViolationException.class
			}, {	// Creación errónea de un Mensaje: text nulo.
				"user1", "incorrecto", null, ConstraintViolationException.class
			}, {	// Creación errónea de un Mensaje: attachment nulo
				"user1", "incorrecto", "incorrecto", ConstraintViolationException.class
			}

		};
		for (int i = 0; i < testingData.length; i++){
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}
	//- An actor who is authenticated must be able to:
	//	o List the messages that he or she's got and reply to them.
	//	o List the messages that he or she's got and forward them
	@Test
	public void driverFindAllByFolderId() {
		final Object testingData[][] = {
			{
				"user1", "Received", null
			}, {
				"user1", "Sent", null
			}, {
				"user3", "Received", null
			}, {
				"user3", "Sent", null
			}, {
				"user8", "Received", null
			}, {
				"user8", "Sent", null
			},
		};
		for (int i = 0; i < testingData.length; i++){
			this.templateFindAllByFolderId((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	//	- An actor who is authenticated as a user must be able to:
	//		o Broadcast a message to the useres who have registered to any of the events that he or she manages.
	@Test
	public void driverSendBroadcast() {

		final Object testingData[][] = {
			{    // Enviar correctamente un broadcast
				"user1", "association1", "correcto", "correcto", null
			}, { // Fallo al enviar un broadcast modificando un evento inexistente
				"user1", "association12", "correcto", "correcto", NumberFormatException.class
			}
		};
		for (int i = 0; i < testingData.length; i++){
			this.templateSendBroadcast((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	@Test
	public void driverResendChirp() {
		final Object testingData[][] = {
			{
				"user1", "message2", "user3", null
			}
		};
		for (int i = 0; i < testingData.length; i++){
			this.templateResendChirp((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}
	@Test
	public void templateSendMessageOverdueLoan() {
		this.authenticate("user2");
		final List<Message> oldMessages = new ArrayList<Message>(this.messageService.findAllByFolder(this.extract("received2")));
		this.messageService.sendMessageOverdueLoan();
		final List<Message> newMessages = new ArrayList<Message>(this.messageService.findAllByFolder(this.extract("received2")));
		Assert.isTrue(newMessages.containsAll(oldMessages));
		Assert.isTrue(newMessages.size() == oldMessages.size() + 3);

		this.unauthenticate();
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String subject, final String text, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Actor cus = this.actorService.findOne(this.extract("user1"));
			final Message m = this.messageService.create();
			m.setText(text);
			m.setTitle(subject);
			m.setRecipient(cus);

			this.messageService.send(m);

			final Actor sender = this.actorService.findByPrincipal();
			final Actor recipient = m.getRecipient();

			Assert.isTrue(m.getSender().equals(sender) && m.getRecipient().equals(recipient));

			final Folder recipientFolder = this.folderService.findSystemFolder(recipient, "Received");
			final Folder senderFolder = this.folderService.findSystemFolder(sender, "Sent");

			final Collection<Message> recipientMessages = this.messageService.findAllByFolderWithNoCheck(recipientFolder.getId());
			final Collection<Message> senderMessages = this.messageService.findAllByFolderWithNoCheck(senderFolder.getId());

			for (final Message r : recipientMessages)
				for (final Message s : senderMessages)
					if (r.getTitle() == s.getTitle() && r.getMoment().equals(s.getMoment()) && r.getRecipient().equals(s.getRecipient()) && r.getText() == s.getText())
						Assert.isTrue(r.getTitle() == s.getTitle() && r.getMoment().equals(s.getMoment()) && r.getRecipient().equals(s.getRecipient()) && r.getText() == s.getText());
			this.messageService.delete(m);

			final Collection<Message> all = this.messageService.findAll();
			Assert.isTrue(!(all.contains(m)));

			this.messageService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateFindAllByFolderId(final String username, final String folderName, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Actor actor = this.actorService.findByPrincipal();
			final Folder folder = this.folderService.findSystemFolder(actor, folderName);
			this.messageService.findAllByFolder(folder.getId());
			this.unauthenticate();

			this.messageService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	private void templateSendBroadcast(final String username, final String associationName, final String subject, final String text, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Actor sender = this.actorService.findByPrincipal();
			final MessageBroadcast cB = new MessageBroadcast();
			cB.setText(text);
			cB.setAssociation(this.associationService.findOne(this.extract(associationName)));
			this.messageService.broadcast(cB);
			this.unauthenticate();

			this.authenticate("user1");
			final Folder recipientFolder = this.folderService.findSystemFolder(this.actorService.findOne(this.extract("user1")), "Received");
			final Collection<Message> messages = this.messageService.findAllByFolder(recipientFolder.getId());
			for (final Message c : messages)
				if (c.getSender() == sender && c.getText() == cB.getText())
					Assert.isTrue(c.getSender() == sender && c.getText() == cB.getText());

			this.unauthenticate();
			this.messageService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	private void templateResendChirp(final String username, final String messageToSend, final String receiver, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		final Message messageToSendExtracted = this.messageService.findOne(this.extract(messageToSend));
		try {
			this.authenticate(username);
			this.messageService.reSend(messageToSendExtracted, this.actorService.findOne(this.extract(receiver)));
			this.unauthenticate();

			this.authenticate(receiver);
			final Folder recipientFolder = this.folderService.findSystemFolder(this.actorService.findOne(this.extract(receiver)), "Received");
			final Collection<Message> messages = this.messageService.findAllByFolder(recipientFolder.getId());
			for (final Message c : messages)
				if (c.getTitle() == messageToSendExtracted.getTitle() && c.getMoment().equals(messageToSendExtracted.getMoment()) && c.getRecipient().equals(messageToSendExtracted.getRecipient()) && c.getText() == messageToSendExtracted.getText())
					Assert.isTrue(c.getTitle() == messageToSendExtracted.getTitle() && c.getMoment().equals(messageToSendExtracted.getMoment()) && c.getRecipient().equals(messageToSendExtracted.getRecipient()) && c.getText() == messageToSendExtracted.getText());
			this.unauthenticate();
			this.messageService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
