
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
import domain.Association;
import domain.Meeting;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MeetingServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private MeetingService		meetingService;

	@Autowired
	private AssociationService	associationService;

	@Autowired
	private UserService			userService;

	Calendar					calendarValida	= new GregorianCalendar(1995, 12, 14);
	Date						fechaValida		= this.calendarValida.getTime();

	Calendar					calendarFutura	= new GregorianCalendar(2020, 12, 14);
	Date						fechaFutura		= this.calendarFutura.getTime();


	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Meeting.
				"user1", this.fechaValida, "mi casa", "http://www.imagen.com.mx/assets/img/imagen_share.png", "blae", "association1", null
			}, {	// Creación errónea de un Meeting: moment null.
				"user1", null, "mi casa", "http://www.imagen.com.mx/assets/img/imagen_share.png", "blae", "association1", ConstraintViolationException.class
			}, {	// Creación errónea de un Meeting: address vacío
				"user1", this.fechaValida, "", "http://www.imagen.com.mx/assets/img/imagen_share.png", "blae", "association1", ConstraintViolationException.class
			}, {	// Creación errónea de un Meeting: agenda null.
				"user1", this.fechaValida, "mi casa", null, "blae", "association1", ConstraintViolationException.class
			}, {	// Creación errónea de un Meeting: issue vacío.
				"user1", this.fechaValida, "mi casa", "http://www.imagen.com.mx/assets/img/imagen_share.png", "", "association1", ConstraintViolationException.class
			}, {	// Creación errónea de un Meeting: Association null.
				"user1", this.fechaValida, "mi casa", "http://www.imagen.com.mx/assets/img/imagen_share.png", "blae", null, NullPointerException.class
			}, {	// Creación errónea de un Meeting: Association inexistente.
				"user1", this.fechaValida, "mi casa", "http://www.imagen.com.mx/assets/img/imagen_share.png", "blae", "association100", NumberFormatException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (Date) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	@Test
	public void driverDisplay() {
		final Object testingData[][] = {
			{		// Display correcto de un Meeting logueado como user. 
				"user1", "meeting1", null
			}, {	// Display correcto de un Meeting sin estar logueado.
				null, "meeting1", null
			}, {	// display correcto de un Meeting logueado como admin.
				"admin", "meeting1", null
			}, {	// Intento de mostrar un Meeting null.
				"user1", null, NullPointerException.class
			}, {	// Intento de mostrar un Meeting que no existe.
				"user1", "meeting100", NumberFormatException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String user, final Date moment, final String address, final String agenda, final String issue, final String associationId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);

			final Association a = this.associationService.findOne(this.extract(associationId));
			final Meeting m = this.meetingService.create(a.getId());

			m.setMoment(moment);
			m.setAddress(address);
			m.setAgenda(agenda);
			m.setIssue(issue);

			final Meeting saved = this.meetingService.save(m);

			this.meetingService.delete(saved);
			this.unauthenticate();
			this.meetingService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateDisplaying(final String username, final String meetingId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			this.meetingService.findOne(this.extract(meetingId));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
