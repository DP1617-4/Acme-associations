
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
import domain.Sanction;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SanctionServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private SanctionService		sanctionService;

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
			{		// Creación correcta de una Sanction.
				"user1", "blae", this.fechaFutura, "association1", "user3", null
			}, {	// Creación errónea de una Sanction: motiff vacío.
				"user1", "", this.fechaFutura, "association1", "user3", ConstraintViolationException.class
			}, {	// Creación errónea de una Sanction: endDate null.
				"user1", "blae", null, "association1", "user3", ConstraintViolationException.class
			}, {	// Creación errónea de una Sanction: Association de la cual el logueado no es el manager.
				"user1", "blae", this.fechaFutura, "association6", "user3", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	@Test
	public void driverDisplay() {
		final Object testingData[][] = {
			{		// Display correcto de una Sanction logueado como manager de la Association.
				"user1", "sanction1", null
			}, {	// Intento de mostrar una Sanction sin estar logueado.
				null, "sanction1", NullPointerException.class
			}, {		// Intento de mostrar una Sanction logueado como admin.
				"admin", "sanction1", NullPointerException.class
			}, {		// Intento de mostrar una Sanction logueado como user que no es manager de la Association.
				"user2", null, NullPointerException.class
			}, {		// Intento de mostrar una Sanction que no existe.
				"user1", "blae", NumberFormatException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String user, final String motiff, final Date endDate, final String associationId, final String userId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);

			final Association association = this.associationService.findOne(this.extract(associationId));
			final User userSanctioned = this.userService.findOne(this.extract(userId));

			final Sanction sanction = this.sanctionService.create(userSanctioned.getId(), association);

			sanction.setMotiff(motiff);
			sanction.setEndDate(endDate);

			final Sanction saved = this.sanctionService.save(sanction);

			this.sanctionService.delete(saved);
			this.unauthenticate();
			this.sanctionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void templateDisplaying(final String username, final String sanctionId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			this.sanctionService.findOneToEdit(this.extract(sanctionId));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
