
package services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Meeting;
import domain.Minutes;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MinutesServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private MeetingService	meetingService;

	@Autowired
	private MinutesService	minutesService;

	@Autowired
	private UserService		userService;

	Calendar				calendarValida	= new GregorianCalendar(1995, 12, 14);
	Date					fechaValida		= this.calendarValida.getTime();

	Calendar				calendarFutura	= new GregorianCalendar(2020, 12, 14);
	Date					fechaFutura		= this.calendarFutura.getTime();


	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Minutes.
				"user1", "http://www.imagen.com.mx/assets/img/imagen_share.png", "meeting1", null
			}, {	// Creación errónea de un Minute: document null.
				"user1", null, "meeting1", ConstraintViolationException.class
			}, {	// Creación errónea de un Minute: document sin formato URL.
				"user1", "document", "meeting1", ConstraintViolationException.class
			}, {	// Creación errónea de un Minute: Meeting null.
				"user1", "document", null, NullPointerException.class
			}, {	// Creación errónea de un Minute: Meeting inexistente.
				"user1", "document", "meeting100", NumberFormatException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	@Test
	public void driverDisplay() {
		final Object testingData[][] = {
			{		// Display correcto de un Minutes logueado como user.
				"user1", "minutes1", null
			}, {	// Display correcto de un Minutes sin estar logueado.
				null, "minutes1", null
			}, {		// Display correcto de un Minutes logueado como admin.
				"admin", "minutes1", null
			}, {		// Intento de mostrar un Minutes null.
				"user1", null, NullPointerException.class
			}, {		// Intento de mostrar un Minutes que no existe.
				"user1", "minutes100", NumberFormatException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverAddParticipant() {
		final Object testingData[][] = {
			{		// Participant añadido correctamente logueado como user.
				"user1", "minutes1", "user4", null
			}, {	// Intento de añadir un participant a un Minutes sin estar logueado.
				null, "minutes1", "user4", null
			}, {		// Participant añadido correctamente logueado como admin.
				"admin", "minutes1", "user4", null
			}, {		// Intento de añadir un participant a un Meeting null.
				"user1", null, "user4", NullPointerException.class
			}, {		// Intento de añadir un participant a un Meeting que no existe.
				"user1", "minutes100", "user4", NumberFormatException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateAddParticipant((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String user, final String document, final String meetingId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);

			final Meeting me = this.meetingService.findOne(this.extract(meetingId));
			final Minutes mi = this.minutesService.create(me.getId());

			mi.setDocument(document);

			final Minutes saved = this.minutesService.save(mi);

			this.minutesService.delete(saved);
			this.unauthenticate();
			this.minutesService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void templateDisplaying(final String username, final String minutesId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			this.minutesService.findOne(this.extract(minutesId));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateAddParticipant(final String username, final String minutesId, final String userId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Minutes minutes = this.minutesService.findOne(this.extract(minutesId));
			final User user = this.userService.findOne(this.extract(userId));
			this.minutesService.addParticipant(user.getId(), minutes.getId());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
