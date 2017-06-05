
package services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Association;
import domain.Request;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RequestServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private RequestService		requestService;

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
			{		// Creaci�n correcta de una Association.
				"user1", "association3", null
			}, {	// Creaci�n err�nea de una Association: name vac�o.
				"user1", "association1", IllegalArgumentException.class
			}, {	// Creaci�n err�nea de una Association: name vac�o.
				"user1", "association2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	@Test
	public void driverDisplay() {
		final Object testingData[][] = {
			{		// Display correcto de una association ya creado y logueado como tal. 
				"user1", "request1", null
			}, {	// Display correcto de un user distinto al que est� logueado.
				null, "request1", null
			}, {		// Intento de mostrar una asociacion que no existe
				"admin", "request1", null
			}, {		// Intento de mostrar una asociacion que no existe
				"user1", null, NullPointerException.class
			}, {		// Intento de mostrar una asociacion que no existe
				"user1", "blae", NumberFormatException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverAccept() {
		final Object testingData[][] = {
			{		// Display correcto de una association ya creado y logueado como tal. 
				"user2", "request1", null
			}, {	// Display correcto de un user distinto al que est� logueado.
				null, "request5", NullPointerException.class
			}, {		// a ver para qu� el user3 envia una peticion para ser miembro de la asociacion de la cual ya es manager de hecho(o colaborador, no me acuerdo)
				"admin", "request2", NullPointerException.class
			}, {		// Intento de mostrar una asociacion que no existe
				"user1", "request3", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateAccept((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	//UN ADMINISTRADOR Y UN USUARIO QUE NI PERTENECE A LA ASOCIACION PUEDEN DENEGAR UNA REQUEST QUE NO ES SUYA
	public void driverDeny() {
		final Object testingData[][] = {
			{		// Display correcto de una association ya creado y logueado como tal. 
				"user2", "request1", null
			}, {	// Display correcto de un user distinto al que est� logueado.
				null, "request5", NullPointerException.class
			}, {		// a ver para qu� el user3 envia una peticion para ser miembro de la asociacion de la cual ya es manager de hecho(o colaborador, no me acuerdo)
				"admin", "request2", NullPointerException.class
			}, {		// Intento de mostrar una asociacion que no existe
				"user1", "request3", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDeny((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String user, final String associationId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);

			final Association association = this.associationService.findOne(this.extract(associationId));

			final Request request = this.requestService.create(association);

			final Request saved = this.requestService.save(request);

			this.requestService.delete(saved);
			this.unauthenticate();
			this.requestService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void templateDisplaying(final String username, final String requestId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			this.requestService.findOne(this.extract(requestId));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateAccept(final String username, final String requestId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Request request = this.requestService.findOne(this.extract(requestId));
			this.requestService.accept(request);
			this.unauthenticate();
			this.requestService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateDeny(final String username, final String requestId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Request request = this.requestService.findOne(this.extract(requestId));
			this.requestService.deny(request);
			this.unauthenticate();
			this.requestService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
