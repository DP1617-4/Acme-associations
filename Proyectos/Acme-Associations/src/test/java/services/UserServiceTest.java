
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
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UserServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private UserService	userService;

	Calendar			calendarValida	= new GregorianCalendar(1995, 12, 14);
	Date				fechaValida		= this.calendarValida.getTime();

	Calendar			calendarFutura	= new GregorianCalendar(2020, 12, 14);
	Date				fechaFutura		= this.calendarFutura.getTime();


	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Customer.
				"correcto", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "http://www.edurne.com", "descripcion", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, null
			}, {	// Creación errónea de un Customer: username vacío.
				"", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "http://www.edurne.com", "descripcion", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: password vacío.
				"correcto", "", "correcto", "correcto", "correcto@bien.com", "1234", "http://www.edurne.com", "descripcion", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: name vacío.
				"correcto", "correcto", "", "correcto", "correcto@bien.com", "1234", "http://www.edurne.com", "descripcion", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: surname vacío.
				"correcto", "correcto", "correcto", "", "correcto@bien.com", "1234", "http://www.edurne.com", "descripcion", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: email vacío.
				"correcto", "correcto", "correcto", "correcto", "", "1234", "http://www.edurne.com", "descripcion", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: phoneNumber vacío.
				"correcto", "correcto", "correcto", "correcto", "correcto@bien.com", "", "http://www.edurne.com", "descripcion", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: username con pocos carácteres.
				"cor", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "http://www.edurne.com", "descripcion", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: password con pocos carácteres.
				"correcto", "cor", "correcto", "correcto", "correcto@bien.com", "1234", "http://www.edurne.com", "descripcion", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: email incorrecto.
				"correcto", "correcto", "correcto", "correcto", "correctobien.com", "1234", "http://www.edurne.com", "descripcion", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: phoneNumber incorrecto.
				"correcto", "correcto", "correcto", "correcto", "correcto@bien.com", "A", "http://www.edurne.com", "descripcion", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: picture vacío.
				"", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "", "descripcion", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: picture incorrecto.
				"", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", ".edurne.com", "descripcion", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: description vacío.
				"", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "http://www.edurne.com", "", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: relationship vacío.
				"", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "http://www.edurne.com", "descripcion", "", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: relationship incorrecto.
				"", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "http://www.edurne.com", "descripcion", "LOE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: fecha vacía.
				"", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "http://www.edurne.com", "descripcion", "LOVE", null, "WOMAN", false, "Country", "state", "province", "city", 1.0, IllegalArgumentException.class
			}, {	// Creación errónea de un Customer: fecha futura.
				"", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "http://www.edurne.com", "descripcion", "LOVE", this.fechaFutura, "WOMAN", false, "Country", "state", "province", "city", 1.0, IllegalArgumentException.class
			}, {	// Creación errónea de un Customer: genre vacío.
				"", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "http://www.edurne.com", "descripcion", "LOVE", this.fechaValida, "", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: genre incorrecto.
				"", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "http://www.edurne.com", "descripcion", "LOVE", this.fechaValida, "WMAN", false, "Country", "state", "province", "city", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: ciudad vacía.
				"", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "http://www.edurne.com", "descripcion", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "", 1.0, ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: fee negativa.
				"", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "http://www.edurne.com", "descripcion", "LOVE", this.fechaValida, "WOMAN", false, "Country", "state", "province", "", -1.0, ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (Date) testingData[i][9], (String) testingData[i][10], (Boolean) testingData[i][11], (String) testingData[i][12], (String) testingData[i][13], (String) testingData[i][14],
				(String) testingData[i][15], (Double) testingData[i][16], (Class<?>) testingData[i][17]);
	}
	@Test
	public void driverDisplaying() {
		final Object testingData[][] = {
			{		// Display correcto de un user ya creado y logueado como tal. 
				"user1", "user1", null
			}, {	// Display correcto de un user distinto al que está logueado.
				"user1", "user2", null
			}, {	// Display erróneo de un user que no existe con uno logueado.
				"user1", "event1", IllegalArgumentException.class
			}, {	// Display correcto de un user, sin estar logueado en el sistema.
				null, "user1", null
			}, {	// Display erróneo de un user que no existe sin estar logueado.
				null, "event1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

//	@Test
//	public void driverLikesMe() {
//		final Object testingData[][] = {
//			{		// Listado correcto de los me gusta a un user, estando logueado como tal. 
//				"user3", "user3", null
//			}, {	// Listado correcto de los me gusta de un user diferente al logueado.
//				"user3", "user1", null
//			}, {	// Listado correcto de los me gusta de un user sin estar logueado.
//				null, "user1", null
//			}, {	// Display erróneo de los me gusta de un user inexistente.
//				"user3", "event1", IllegalArgumentException.class
//			}
//		};
//		for (int i = 0; i < testingData.length; i++)
//			this.templateLikesMe((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
//	}

	//	- An actor who is authenticated as an administrator must be able to:
	//		o Run a process to update the total monthly fees that the useres would have to pay. Recall that useres must not be aware of the simulation.
//	@Test
//	public void driverSumFee() {
//		final Object testingData[][] = {
//			{		// Suma correcta de la cuota a un user. 
//				"user3", null
//			}, {	// Fallo al intentar sumar a fee a algo que no es un user.
//				"event1", IllegalArgumentException.class
//			}
//		};
//		for (int i = 0; i < testingData.length; i++)
//			this.templateSumFee((String) testingData[i][0], (Class<?>) testingData[i][1]);
//	}

	//	- An actor who is authenticated as an administrator must be able to:
	//		o Ban a user, that is, to disable his or her account.
//	@Test
//	public void driverBan() {
//		final Object testingData[][] = {
//			{		// Baneo (no se muestra en el listar normal de users) y desbaneo correcto de un user. 
//				"user3", null
//			}, {	// Fallo al banear algo que no es un user.
//				"event1", NullPointerException.class
//			}
//		};
//		for (int i = 0; i < testingData.length; i++)
//			this.templateBan((String) testingData[i][0], (Class<?>) testingData[i][1]);
//	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String password, final String name, final String surname, final String email, final String phone, final String picture, final String description, final String relationshipType,
		final Date birthDate, final String genre, final Boolean banned, final String country, final String state, final String province, final String city, final Double cumulatedFee, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			final User c = this.userService.create();
			c.getUserAccount().setUsername(username);
			c.getUserAccount().setPassword(password);
			c.setName(name);
			c.setSurname(surname);
			c.setEmail(email);
			c.setPhoneNumber(phone);
			
			this.userService.save(c);
			this.userService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateDisplaying(final String username, final String userId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			this.userService.findOne(this.extract(userId));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

//	protected void templateLikesMe(final String username, final String userId, final Class<?> expected) {
//		Class<?> caught;
//		caught = null;
//		try {
//			this.authenticate(username);
//			final Collection<User> users = this.userService.findAllLikingMe(this.extract(userId));
//		} catch (final Throwable oops) {
//			caught = oops.getClass();
//		}
//		this.checkExceptions(expected, caught);
//	}

//	protected void templateSumFee(final String userId, final Class<?> expected) {
//		Class<?> caught;
//		caught = null;
//		try {
//			final Double feeAnterior = this.userService.findOne(this.extract(userId)).getCumulatedFee();
//			this.userService.sumFee(this.userService.findOne(this.extract(userId)));
//			Assert.isTrue(feeAnterior < this.userService.findOne(this.extract(userId)).getCumulatedFee());
//		} catch (final Throwable oops) {
//			caught = oops.getClass();
//		}
//		this.checkExceptions(expected, caught);
//	}

//	protected void templateBan(final String userId, final Class<?> expected) {
//		Class<?> caught;
//		caught = null;
//		try {
//			this.authenticate("admin");
//			this.userService.banUser(this.extract(userId));
//			final Collection<User> sinBaneados = this.userService.findAllNotBanned();
//			if (sinBaneados.contains(this.userService.findOne(this.extract(userId))))
//				throw new Exception("El user baneado se lista en la lista sin baneados");
//			this.userService.banUser(this.extract(userId));
//		} catch (final Throwable oops) {
//			caught = oops.getClass();
//		}
//		this.checkExceptions(expected, caught);
//	}

//	protected void templateLoginBan(final String userId, final Class<?> expected) {
//		Class<?> caught;
//		caught = null;
//		try {
//			this.authenticate("admin");
//			this.userService.banUser(this.extract(userId));
//			this.unauthenticate();
//			this.authenticate(userId);
//			this.unauthenticate();
//			this.authenticate("admin");
//			this.userService.banUser(this.extract(userId));
//		} catch (final Throwable oops) {
//			caught = oops.getClass();
//		}
//		this.checkExceptions(expected, caught);
//	}

}
