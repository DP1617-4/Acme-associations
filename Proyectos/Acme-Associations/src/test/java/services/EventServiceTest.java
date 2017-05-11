
package services;

import java.util.Calendar;
import java.util.Collection;
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
import domain.Event;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EventServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private EventService	eventService;

	Calendar				calendarValida	= new GregorianCalendar(1995, 12, 14);
	Date					fechaValida		= this.calendarValida.getTime();

	Calendar				calendarFutura	= new GregorianCalendar(2020, 12, 14);
	Date					fechaFutura		= this.calendarFutura.getTime();


	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Event.
				"manager1", "blae", this.fechaFutura, "equisdejajajaxdxdxd", "http://www.imagen.com.mx/assets/img/imagen_share.png", 10, null
			}, {	// Creación errónea de un Event: title vacío.
				"manager1", null, this.fechaFutura, "equisdejajajaxdxdxd", "http://www.imagen.com.mx/assets/img/imagen_share.png", 1, ConstraintViolationException.class
			}, {	// Creación errónea de un Event: description vacío.
				"manager1", "blae", this.fechaFutura, null, "http://www.imagen.com.mx/assets/img/imagen_share.png", 1, ConstraintViolationException.class
			}, {	// Creación errónea de un Event: picture que no es url.
				"manager1", "blae", this.fechaFutura, "equisdejajajaxdxdxd", "no soy una url jiji", 1, ConstraintViolationException.class
			}, {	// Creación errónea de un Event: moment nulo vacío.
				"manager1", "blae", null, "equisdejajajaxdxdxd", "http://www.imagen.com.mx/assets/img/imagen_share.png", 1, ConstraintViolationException.class
			}, {	// Creación errónea de un Event: numberSeat negativo.
				"manager1", "blae", this.fechaFutura, "equisdejajajaxdxdxd", "http://www.imagen.com.mx/assets/img/imagen_share.png", -1, ConstraintViolationException.class
			}, {	// Creación errónea de un Event: no es un manager el logueado.
				"chorbi1", "blae", this.fechaFutura, "equisdejajajaxdxdxd", "http://www.imagen.com.mx/assets/img/imagen_share.png", 1, ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreationDelete((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (int) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	@Test
	public void driverListOwn() {
		final Object testingData[][] = {
			{		// Chorbi con su id
				"chorbi1", 91, null
			}, {	// Un chorbi
				"manager1", 91, IllegalArgumentException.class
			}, {	// Alguien no logueado
				null, 91, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListOwn((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverList() {
		final Object testingData[][] = {
			{		// Manager con su id
				"manager1", 97, null
			}, {	// Un chorbi
				"chorbi1", 97, null
			}, {	// Alguien no logueado
				null, 97, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateList((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverRegister() {
		final Object testingData[][] = {
			{		// Un chorbi registrándose a un event
				"chorbi1", 142, null
			}, {	// Un manager registrándose a un event
				"manager1", 142, IllegalArgumentException.class
			}, {	// Alguien no logueado
				null, 142, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateRegister((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreationDelete(final String username, final String title, final Date moment, final String description, final String picture, final int numberSeat, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Event e = this.eventService.create();

			e.setDescription(description);
			e.setMoment(moment);
			e.setNumberSeat(numberSeat);
			e.setPicture(picture);
			e.setTitle(title);

			final Event saved = this.eventService.save(e);
			this.eventService.delete(saved);
			this.unauthenticate();
			this.eventService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateListOwn(final String username, final int managerId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Collection<Event> e = this.eventService.findAllByPrincipalChorbi();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateList(final String username, final int managerId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Collection<Event> e = this.eventService.findAll();
			final Collection<Event> ev = this.eventService.findAllEventsInOneMonth();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateRegister(final String username, final int eventId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Event e = this.eventService.findOne(eventId);
			this.eventService.register(e);
			this.eventService.register(e);
			this.unauthenticate();
			this.eventService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
