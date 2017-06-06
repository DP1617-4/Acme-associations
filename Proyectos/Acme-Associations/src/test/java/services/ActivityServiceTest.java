
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
import domain.Association;
import domain.Item;
import domain.Place;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ActivityServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private ActivityService		activityService;

	@Autowired
	private AssociationService	associationService;

	@Autowired
	private UserService			userService;

	@Autowired
	private PlaceService		placeService;

	@Autowired
	private ItemService			itemService;

	Calendar					calendarValida	= new GregorianCalendar(2017, 06, 06);
	Date						fechaValida		= this.calendarValida.getTime();

	Calendar					calendarPasada	= new GregorianCalendar(1995, 12, 14);
	Date						fechaPasada		= this.calendarPasada.getTime();

	Calendar					calendarFutura	= new GregorianCalendar(2020, 12, 14);
	Date						fechaFutura		= this.calendarFutura.getTime();


	// 
	// Tests ---------------------------------------------------------------
	// Test para la creacion y borrado de una actividad
	@Test
	public void driverCreationDelete() {
		final Object testingData[][] = {
			{			// Creación correcta de un Activity: Manager.
				"user1", "association1", null, null, null, "Actividad 1", "esta es una actividad de prueba", 10, this.fechaValida, this.fechaFutura, true, null
			}, {		// Creación correcta de un Activity: Colaborador.
				"user3", "association1", null, null, null, "Actividad 1", "esta es una actividad de prueba", 10, this.fechaValida, this.fechaFutura, true, null
			}, {		// creacion Incorrecta: Usuario sin permisos.
				"user8", "association1", null, null, null, "Actividad 1", "esta es una actividad de prueba", 10, this.fechaValida, this.fechaFutura, true, IllegalArgumentException.class
			}, {		// creacion Incorrecta: Associacion nula.
				"user1", null, null, null, null, "Actividad 1", "esta es una actividad de prueba", 10, this.fechaValida, this.fechaFutura, true, NullPointerException.class
			}, {		// creacion Incorrecta: nombre nulo.
				"user1", "association1", null, null, null, null, "esta es una actividad de prueba", 10, this.fechaValida, this.fechaFutura, true, ConstraintViolationException.class
			}, {		// creacion Incorrecta: descripcion nula.
				"user1", "association1", null, null, null, "Actividad 1", null, 10, this.fechaValida, this.fechaFutura, true, ConstraintViolationException.class
			}, {		// creacion Incorrecta: maximo numero de attendants nulo.
				"user1", "association1", null, null, null, "Actividad 1", "esta es una actividad de prueba", null, this.fechaValida, this.fechaFutura, true, ConstraintViolationException.class
			}, {		// creacion Incorrecta: maximo numero de attendants negativo.
				"user1", "association1", null, null, null, "Actividad 1", "esta es una actividad de prueba", -1, this.fechaValida, this.fechaFutura, true, ConstraintViolationException.class
			}, {		// creacion Incorrecta: fecha inicio pasada.
				"user1", "association1", null, null, null, "Actividad 1", "esta es una actividad de prueba", -1, this.fechaPasada, this.fechaFutura, true, ConstraintViolationException.class
			}, {		// creacion Incorrecta: fecha inicio anterior a fecha fin.
				"user1", "association1", null, null, null, "Actividad 1", "esta es una actividad de prueba", -1, this.fechaFutura, this.fechaValida, true, ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreationDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Integer) testingData[i][7], (Date) testingData[i][8], (Date) testingData[i][9], (Boolean) testingData[i][10], (Class<?>) testingData[i][11]);
	}
	@Test
	public void driverAddAttendant() {
		final Object testingData[][] = {
			{		// Apuntarse y desapuntarse con exito: Manager
				"user1", "activity1", "user1", null
			}, {	// Apuntarse y desapuntarse con exito: Collaborator
				"user3", "activity1", "user3", null
			}, {	// Apuntarse y desapuntarse con exito: Asociado
				"user8", "activity1", "user8", null
			}, {	// Apuntarse y desapuntarse con error: Sin loguear
				null, "activity1", "user1", NullPointerException.class
			}, {	// Apuntar y desapuntara otro con exito: Manager
				"user1", "activity1", "user3", null
			}, {	// Apuntar y desapuntara otro con exito: Collaborator
				"user3", "activity1", "user1", null
			}, {	// Apuntar y desapuntara otro con exito: Asociado
				"user8", "activity1", "user1", null
			}, {	// Apuntar y desapuntara otro con error: No en la misma asociacion
				"user1", "activity5", "user9", IllegalArgumentException.class
			}, {	// Apuntarse y desapuntarse con error: No en la misma asociacion
				"user9", "activity5", "user9", IllegalArgumentException.class
			}, {	// Apuntar y desapuntar con error: No en la misma asociacion
				"user9", "activity5", "user5", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++){
			this.templateAddAttendant((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
		}
	}

	@Test
	public void driverSetWinner() {
		final Object testingData[][] = {
			{		// Añadir ganador con exito: Manager
				"user1", "activity1", "user3", "item2", null
			}, {	// Añadir ganador con exito: Collaborator
				"user3", "activity1", "user1", "item2", null
			}, {	// Añadir ganador con error: Asociado
				"user8", "activity1", "user3", "item2", IllegalArgumentException.class
			}, {	// Añadir ganador con error: sin loguear
				null, "activity1", "user3", "item2", NullPointerException.class
			}, {	// Añadir ganador con error: sin estar en la actividad
				"user1", "activity1", "user8", "item2", IllegalArgumentException.class
			}, {	// Añadir ganador con error: ganador fuera de la asociacion
				"user1", "activity1", "user9", "item2", IllegalArgumentException.class
			}, {	// Añadir ganador con error: usuario logueado fuera de la asociacion
				"user9", "activity1", "user1", "item2", IllegalArgumentException.class
			}, {	// Añadir ganador con error: usuario nulo
				"user2", "activity1", null, "item2", NullPointerException.class
			}, {	// Añadir ganador con error: item nulo
				"user1", "activity1", "user3", null, NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++){
			this.templateSetWinner((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	// Templates ----------------------------------------------------------

	protected void templateCreationDelete(final String username, final String association, final String place, final String winner, final String item, final String name, final String description, final Integer maximumAttendants, final Date startMoment,
		final Date endMoment, final Boolean publicActivity, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Association a = this.associationService.findOne(this.extract(association));
			final Activity activity = this.activityService.create(a);

			if (place != null) {
				final Place p = this.placeService.findOne(this.extract(place));
				activity.setPlace(p);
			}
			if (item != null) {
				final Item i = this.itemService.findOne(this.extract(item));
				activity.setItem(i);
			}

			activity.setName(name);
			activity.setDescription(description);
			activity.setMaximumAttendants(maximumAttendants);
			activity.setEndMoment(endMoment);
			activity.setStartMoment(startMoment);
			activity.setPublicActivity(publicActivity);

			final Activity saved = this.activityService.save(activity);
			this.activityService.delete(saved);
			this.unauthenticate();
			this.activityService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateAddAttendant(final String username, final String activity, final String user, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Activity a = this.activityService.findOne(this.extract(activity));
			final User u = this.userService.findOne(this.extract(user));
			this.activityService.addParticipant(u, a);
			this.activityService.addParticipant(u, a);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateSetWinner(final String username, final String activity, final String user, final String item, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Activity a = this.activityService.findOne(this.extract(activity));
			final User u = this.userService.findOne(this.extract(user));
			final Item i = this.itemService.findOne(this.extract(item));
			this.activityService.setWinner(a, u, i);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test(expected = ConstraintViolationException.class)
	public void MomentsValidatorTest() {
		this.authenticate("user1");
		final Association a = this.associationService.findOne(this.extract("association1"));
		final Activity activity = this.activityService.create(a);

		activity.setName("Prueba");
		activity.setDescription("Prueba tests");
		activity.setMaximumAttendants(55);
		activity.setEndMoment(this.fechaValida);
		activity.setStartMoment(this.fechaFutura);
		activity.setPublicActivity(false);

		final Activity saved = this.activityService.save(activity);
		this.unauthenticate();
		this.activityService.flush();
	}
}
