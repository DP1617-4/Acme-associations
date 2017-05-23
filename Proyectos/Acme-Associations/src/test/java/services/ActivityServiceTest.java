
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
import domain.Activity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ActivityServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private ActivityService	activityService;

	Calendar				calendarValida	= new GregorianCalendar(1995, 12, 14);
	Date					fechaValida		= this.calendarValida.getTime();

	Calendar				calendarFutura	= new GregorianCalendar(2020, 12, 14);
	Date					fechaFutura		= this.calendarFutura.getTime();


	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Activity.
				"manager1", "blae", this.fechaFutura, "equisdejajajaxdxdxd", "http://www.imagen.com.mx/assets/img/imagen_share.png", 10, null
			}, {	// Creación errónea de un Activity: title vacío.
				"manager1", null, this.fechaFutura, "equisdejajajaxdxdxd", "http://www.imagen.com.mx/assets/img/imagen_share.png", 1, ConstraintViolationException.class
			}, {	// Creación errónea de un Activity: description vacío.
				"manager1", "blae", this.fechaFutura, null, "http://www.imagen.com.mx/assets/img/imagen_share.png", 1, ConstraintViolationException.class
			}, {	// Creación errónea de un Activity: picture que no es url.
				"manager1", "blae", this.fechaFutura, "equisdejajajaxdxdxd", "no soy una url jiji", 1, ConstraintViolationException.class
			}, {	// Creación errónea de un Activity: moment nulo vacío.
				"manager1", "blae", null, "equisdejajajaxdxdxd", "http://www.imagen.com.mx/assets/img/imagen_share.png", 1, ConstraintViolationException.class
			}, {	// Creación errónea de un Activity: numberSeat negativo.
				"manager1", "blae", this.fechaFutura, "equisdejajajaxdxdxd", "http://www.imagen.com.mx/assets/img/imagen_share.png", -1, ConstraintViolationException.class
			}, {	// Creación errónea de un Activity: no es un manager el logueado.
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
			{		// Un chorbi registrándose a un activity
				"chorbi1", 142, null
			}, {	// Un manager registrándose a un activity
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
			final Activity e = this.activityService.create();

			e.setDescription(description);

			final Activity saved = this.activityService.save(e);
			this.activityService.delete(saved);
			this.unauthenticate();
			this.activityService.flush();
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
			//			final Collection<Activity> e = this.activityService.findAllByPrincipalChorbi();
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
			this.activityService.findAll();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateRegister(final String username, final int activityId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			this.activityService.findOne(activityId);
			//			this.activityService.register(e);
			//			this.activityService.register(e);
			this.unauthenticate();
			this.activityService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
