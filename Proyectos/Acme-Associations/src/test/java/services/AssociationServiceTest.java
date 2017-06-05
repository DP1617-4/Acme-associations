
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AssociationServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private AssociationService	associationService;

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
				"user1", "sectasuicida", "aqui morimos todos jaja", "el infierno", this.fechaValida, "https://www.merriam-webster.com/dictionary/blae", "ultimo dia para inscribirse", "http://www.imagen.com.mx/assets/img/imagen_share.png", null
			}, {	// Creación errónea de una Association: name vacío.
				"user1", "", "aqui morimos todos jaja", "el infierno", this.fechaValida, "https://www.merriam-webster.com/dictionary/blae", "ultimo dia para inscribirse", "http://www.imagen.com.mx/assets/img/imagen_share.png",
				ConstraintViolationException.class
			}, {	// Creación errónea de una Association: description vacío.
				"user1", "sectasuicida", "", "el infierno", this.fechaValida, "https://www.merriam-webster.com/dictionary/blae", "ultimo dia para inscribirse", "http://www.imagen.com.mx/assets/img/imagen_share.png", ConstraintViolationException.class
			}, {	// Creación errónea de una Association: creationDate null.
				"user1", "sectasuicida", "aqui morimos todos jaja", "el infierno", null, "https://www.merriam-webster.com/dictionary/blae", "ultimo dia para inscribirse", "http://www.imagen.com.mx/assets/img/imagen_share.png",
				ConstraintViolationException.class
			}, {	// Creación errónea de una Association: creationDate futuro.
				"user1", "sectasuicida", "aqui morimos todos jaja", "el infierno", this.fechaFutura, "https://www.merriam-webster.com/dictionary/blae", "ultimo dia para inscribirse", "http://www.imagen.com.mx/assets/img/imagen_share.png",
				ConstraintViolationException.class
			}, {	// Creación errónea de una Association: statutes vacío.
				"user1", "sectasuicida", "aqui morimos todos jaja", "el infierno", this.fechaValida, "", "ultimo dia para inscribirse", "http://www.imagen.com.mx/assets/img/imagen_share.png", ConstraintViolationException.class
			}, {	// Creación errónea de una Association: statutes sin formato url.
				"user1", "sectasuicida", "aqui morimos todos jaja", "el infierno", this.fechaValida, "blae", "ultimo dia para inscribirse", "http://www.imagen.com.mx/assets/img/imagen_share.png", ConstraintViolationException.class
			}, {	// Creación errónea de una Association: picture sin formato url.
				"user1", "sectasuicida", "aqui morimos todos jaja", "el infierno", this.fechaValida, "https://www.merriam-webster.com/dictionary/blae", "ultimo dia para inscribirse", "jijijaja", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Date) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(Class<?>) testingData[i][8]);
	}
	@Test
	public void driverDisplay() {
		final Object testingData[][] = {
			{		// Display correcto de una Association ya creada y logueado como user. 
				"user1", "association1", null
			}, {	// Display correcto de una Asociation sin haberse logueado.
				null, "association1", null
			}, {		// Intento de mostrar una Association que no existe.
				"user1", "association100", NumberFormatException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverClose() {
		final Object testingData[][] = {
			{		// Cierre de una Association por su manager.
				"user1", "association1", null
			}, {	// Intento de cierre de una Association por parte de alguien sin loguearse.
				null, "association1", IllegalArgumentException.class
			}, {	// Intento de cierre de una Association por parte de un usuario de la cual es solo colaborador.
				"user2", "association1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateClose((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverBan() {
		final Object testingData[][] = {
			{		// Baneo de una Association por parte del admin.
				"admin", "association1", null
			}, {	// Intento de banear una Association por parte de alguien no logueado.
				null, "association1", IllegalArgumentException.class
			}, {	// Intento de banear una Association por parte de un usuario cualquiera.
				"user2", "association1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateBan((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String user, final String name, final String description, final String address, final Date creationDate, final String statutes, final String announcements, final String picture, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);
			final Association a = this.associationService.create();
			a.setName(name);
			a.setDescription(description);
			a.setAddress(address);
			a.setCreationDate(creationDate);
			a.setStatutes(statutes);
			a.setAnnouncements(announcements);
			a.setPicture(picture);

			final Association saved = this.associationService.save(a);
			this.unauthenticate();
			this.associationService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateDisplaying(final String username, final String associationId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			this.associationService.findOne(this.extract(associationId));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateClose(final String username, final String associationId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Association a = this.associationService.findOne(this.extract(associationId));
			this.associationService.closeAssociationByManager(a.getId());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateBan(final String username, final String associationId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Association a = this.associationService.findOne(this.extract(associationId));
			this.associationService.banAssociation(a.getId());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void testRandom() {
		final Association a = this.associationService.getRandomAssociation();
		this.associationService.checkClosedBanned(a);
	}

}
