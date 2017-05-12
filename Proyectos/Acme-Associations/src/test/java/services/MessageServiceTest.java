
package services;

import java.util.ArrayList;
import java.util.Collection;

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
import domain.Comment;
import domain.Folder;
import domain.Message;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private MessageService		chirpService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;

	@Autowired
	private ActorService		chorbiService;

	@Autowired
	private AssociationService	associationService;


	// Tests ---------------------------------------------------------------
	//An actor who is authenticated must be able to:
	//	o Exchange chirps with other actors.
	//	o Erase his or her chirps, which requires previous confirmation
	@SuppressWarnings("unchecked")
	@Test
	public void driverCreation() {
		final Collection<String> attachments = new ArrayList<String>();
		final String url = "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png";
		attachments.add(url);
		final Collection<String> attachmentsEmpty = new ArrayList<String>();
		final Collection<String> attachmentsFull = new ArrayList<String>();
		final Collection<String> attachmentWrong = new ArrayList<String>();
		for (int i = 0; i < 20; i++)
			attachmentsFull.add(url);
		final String urlWrong = "Esto no es un link";
		attachmentWrong.add(urlWrong);

		final Object testingData[][] = {
			{		// Creación correcta de un Chirp: Un adjunto
				"chorbi1", "correcto", "correcto", attachments, null
			}, {		// Creación correcta de un Chirp: Sin adjuntos;
				"chorbi1", "correcto", "correcto", attachmentsEmpty, null
			}, {		// Creación correcta de un Chirp: Muchos adjuntos;
				"chorbi1", "correcto", "correcto", attachmentsFull, null
			}, {	// Creación incorrecta de un Chirp: Admin intenta enviar un chirp, pero no tiene carpetas
				"admin", "incorrecto", "incorrecto", attachments, NullPointerException.class
			}, {	// Creación errónea de un Chirp: title vacío.
				"chorbi1", "", "incorrecto", attachments, ConstraintViolationException.class
			}, {	// Creación errónea de un Chirp: title nulo.
				"chorbi1", null, "incorrecto", attachments, ConstraintViolationException.class
			}, {	// Creación errónea de un Chirp: text vacío.
				"chorbi1", "incorrecto", "", attachments, ConstraintViolationException.class
			}, {	// Creación errónea de un Chirp: text nulo.
				"chorbi1", "incorrecto", null, attachments, ConstraintViolationException.class
			}, {	// Creación errónea de un Chirp: attachment nulo
				"chorbi1", "incorrecto", "incorrecto", null, ConstraintViolationException.class
			}, {	// Creación errónea de un Chirp: attachment Mal
				"chorbi1", "incorrecto", "incorrecto", attachmentWrong, ConstraintViolationException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Collection<String>) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	//- An actor who is authenticated must be able to:
	//	o List the chirps that he or she's got and reply to them.
	//	o List the chirps that he or she's got and forward them
	@Test
	public void driverFindAllByFolderId() {
		final Object testingData[][] = {
			{
				"chorbi1", "Received", null
			}, {
				"chorbi1", "Sent", null
			}, {
				"chorbi2", "Received", null
			}, {
				"chorbi2", "Sent", null
			}, {
				"chorbi3", "Received", null
			}, {
				"chorbi3", "Sent", null
			},
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindAllByFolderId((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	//	- An actor who is authenticated as a manager must be able to:
	//		o Broadcast a chirp to the chorbies who have registered to any of the events that he or she manages.
	@Test
	public void driverSendBroadcast() {
		final Collection<String> attachments = new ArrayList<String>();
		final String url = "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png";
		attachments.add(url);

		final Object testingData[][] = {
			{    // Enviar correctamente un broadcast
				"manager1", "event4", "correcto", "correcto", attachments, null
			}, { // Fallo al enviar un broadcast modificando un evento inexistente
				"manager1", "event12", "correcto", "correcto", attachments, NumberFormatException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSendBroadcast((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Collection<String>) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	@Test
	public void driverResendChirp() {
		final Collection<String> attachments = new ArrayList<String>();
		final String url = "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png";
		attachments.add(url);

		final Object testingData[][] = {
			{
				"chorbi1", "chirp2", "chorbi3", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateResendChirp((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String subject, final String text, final Collection<String> attachment, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final User cus = this.chorbiService.findOne(this.extract("chorbi2"));
			final Message m = this.chirpService.create();
			m.setText(text);
			m.setTitle(subject);
			m.setRecipient(cus);

			this.chirpService.send(m);

			final User sender = this.chorbiService.findByPrincipal();
			final User recipient = (User) m.getRecipient();

			Assert.isTrue(m.getSender().equals(sender) && m.getRecipient().equals(recipient));

			final Folder recipientFolder = this.folderService.findSystemFolder(recipient, "Received");
			final Folder senderFolder = this.folderService.findSystemFolder(sender, "Sent");

			final Collection<Comment> recipientChirps = this.chirpService.findAllByFolderWithNoCheck(recipientFolder.getId());
			final Collection<Comment> senderChirps = this.chirpService.findAllByFolderWithNoCheck(senderFolder.getId());

			for (final Comment r : recipientChirps)
				for (final Comment s : senderChirps)
					if (r.getTitle() == s.getTitle() && r.getMoment().equals(s.getMoment()) && r.getRecipient().equals(s.getRecipient()) && r.getText() == s.getText())
						Assert.isTrue(r.getTitle() == s.getTitle() && r.getMoment().equals(s.getMoment()) && r.getRecipient().equals(s.getRecipient()) && r.getText() == s.getText());
			this.chirpService.delete(m);

			final Collection<Message> all = this.chirpService.findAll();
			Assert.isTrue(!(all.contains(m)));

			this.chirpService.flush();
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
			final User actor = this.chorbiService.findByPrincipal();
			final Folder folder = this.folderService.findSystemFolder(actor, folderName);
			this.chirpService.findAllByFolder(folder.getId());
			this.unauthenticate();

			this.chirpService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	private void templateSendBroadcast(final String username, final String eventName, final String subject, final String text, final Collection<String> attachments, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Actor sender = this.actorService.findByPrincipal();
			final Message cB = new Message();
			cB.setTitle(subject);
			cB.setText(text);
			cB.setEvent(this.associationService.findOne(this.extract(eventName)));
			this.chirpService.broadcast(cB);
			this.unauthenticate();

			this.authenticate("chorbi1");
			final Folder recipientFolder = this.folderService.findSystemFolder(this.chorbiService.findOne(this.extract("chorbi1")), "Received");
			final Collection<Message> chirps = this.chirpService.findAllByFolder(recipientFolder.getId());
			for (final Message c : chirps)
				if (c.getTitle() == cB.getTitle() && c.getSender() == sender && c.getText() == cB.getText())
					Assert.isTrue(c.getTitle() == cB.getTitle() && c.getSender() == sender && c.getText() == cB.getText());

			this.unauthenticate();
			this.chirpService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	private void templateResendChirp(final String username, final String chirpToSend, final String receiver, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		final Message chirpToSendExtracted = this.chirpService.findOne(this.extract(chirpToSend));
		try {
			this.authenticate(username);
			this.chirpService.reSend(chirpToSendExtracted, this.chorbiService.findOne(this.extract(receiver)));
			this.unauthenticate();

			this.authenticate(receiver);
			final Folder recipientFolder = this.folderService.findSystemFolder(this.chorbiService.findOne(this.extract(receiver)), "Received");
			final Collection<Message> chirps = this.chirpService.findAllByFolder(recipientFolder.getId());
			for (final Message c : chirps)
				if (c.getTitle() == chirpToSendExtracted.getTitle() && c.getMoment().equals(chirpToSendExtracted.getMoment()) && c.getRecipient().equals(chirpToSendExtracted.getRecipient()) && c.getText() == chirpToSendExtracted.getText())
					Assert.isTrue(c.getTitle() == chirpToSendExtracted.getTitle() && c.getMoment().equals(chirpToSendExtracted.getMoment()) && c.getRecipient().equals(chirpToSendExtracted.getRecipient()) && c.getText() == chirpToSendExtracted.getText());
			this.unauthenticate();
			this.chirpService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}