
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
import domain.Section;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SectionServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private SectionService		sectionService;

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
			{		// Creación correcta de una Association.
				"user1", "blae", "association1", "user1", null
			}, {	// Creación errónea de una Association: name vacío.
				"user1", "", "association1", "user1", ConstraintViolationException.class
			}, {	// Creación errónea de una Association: name vacío.
				"user1", "blae", "association1", "user5", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	@Test
	public void driverDisplay() {
		final Object testingData[][] = {
			{		// Display correcto de una association ya creado y logueado como tal. 
				"user1", "section1", null
			}, {	// Display correcto de un user distinto al que está logueado.
				null, "section1", null
			}, {		// Intento de mostrar una asociacion que no existe
				"admin", "section1", null
			}, {		// Intento de mostrar una asociacion que no existe
				"user1", null, NullPointerException.class
			}, {		// Intento de mostrar una asociacion que no existe
				"user1", "blae", NumberFormatException.class
			}, {		// Intento de mostrar una asociacion que no existe
				"user1", "section7", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverResponsible() {
		final Object testingData[][] = {
			{		// Display correcto de una association ya creado y logueado como tal. 
				"user1", "section1", "user1", null
			}, {	// Display correcto de un user distinto al que está logueado.
				null, "section1", "user1", null
			}, {		// Intento de mostrar una asociacion que no existe
				"admin", "section1", "user1", null
			}, {		// Intento de mostrar una asociacion que no existe
				"user1", "section2", "user1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateResponsible((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	@Test
	public void driverResponsiblePrincipal() {
		final Object testingData[][] = {
			{		// Display correcto de una association ya creado y logueado como tal. 
				"user1", "section1", null
			}, {	// Display correcto de un user distinto al que está logueado.
				null, "section1", IllegalArgumentException.class
			}, {		// Intento de mostrar una asociacion que no existe
				"admin", "section1", IllegalArgumentException.class
			}, {		// Intento de mostrar una asociacion que no existe
				"user1", "section2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateResponsiblePrincipal((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String user, final String name, final String associationId, final String userId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);

			final Association association = this.associationService.findOne(this.extract(associationId));
			final User u = this.userService.findOne(this.extract(userId));

			final Section section = this.sectionService.create(association);

			section.setName(name);
			section.setUser(u);

			final Section saved = this.sectionService.save(section);

			this.sectionService.delete(saved);
			this.unauthenticate();
			this.sectionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void templateDisplaying(final String username, final String sectionId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			this.sectionService.findOne(this.extract(sectionId));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateResponsible(final String username, final String sectionId, final String userId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Section section = this.sectionService.findOne(this.extract(sectionId));
			final User u = this.userService.findOne(this.extract(userId));
			this.sectionService.checkResponsible(u, section);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateResponsiblePrincipal(final String username, final String sectionId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Section section = this.sectionService.findOne(this.extract(sectionId));
			this.sectionService.checkResponsiblePrincipal(section.getId());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
