
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
	public void driverCreation1() {
		final Object testingData[][] = {
			{		// Creación correcta de un User.
				"username10", "password", "nombre", "apellidos", "email@gmail.com", "2345", "casita", "1234", null
			}, {	// Creación errónea de un User: name vacío.
				"username11", "password", "", "apellidos", "email@gmail.com", "2345", "casita", "1234", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreation2() {
		final Object testingData[][] = {
			{	// Creación errónea de un User: surname vacío.
				"username12", "password", "nombre", "", "email@gmail.com", "2345", "casita", "1234", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreation3() {
		final Object testingData[][] = {
			{	// Creación errónea de un User: email raro.
				"username13", "password", "nombre", "apellidos", "jiji", "2345", "casita", "1234", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreation4() {
		final Object testingData[][] = {
			{	// Creación errónea de un User: email vacío.
				"username14", "password", "nombre", "apellidos", "", "2345", "casita", "1234", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreation5() {
		final Object testingData[][] = {
			{	// Creación errónea de un User: phone vacío.
				"username15", "password", "nombre", "apellidos", "email@gmail.com", "", "casita", "1234", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreation6() {
		final Object testingData[][] = {
			{	// Creación errónea de un User: address vacío.
				"username16", "password", "nombre", "apellidos", "email@gmail.com", "2345", "", "1234", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreation7() {
		final Object testingData[][] = {
			{	// Creación errónea de un User: id vacío.
				"username17", "password", "nombre", "apellidos", "email@gmail.com", "2345", "casita", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreation8() {
		final Object testingData[][] = {
			{		// Creación erronea de un User: username vacío.
				"", "password", "nombre", "apellidos", "email@gmail.com", "2345", "casita", "1234", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreation9() {
		final Object testingData[][] = {
			{		// Creación erronea de un User: password vacío.
				"username18", "", "nombre", "apellidos", "email@gmail.com", "2345", "casita", "1234", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreation10() {
		final Object testingData[][] = {
			{		// Creación erronea de un User: password con pocos caracteres.
				"username19", "pass", "nombre", "apellidos", "email@gmail.com", "2345", "casita", "1234", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverDisplaying() {
		final Object testingData[][] = {
			{		// Display correcto de un user ya creado y logueado como tal. 
				"user1", "user1", null
			}, {	// Display correcto de un user distinto al que está logueado.
				"admin", "user1", null
			}, {	// Display correcto de un user, sin estar logueado en el sistema.
				null, "user1", null
			}, {	// Display erroneo de un user que no existe.
				"user1", "user100", NumberFormatException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverPhoneValidator() {
		final Object testingData[][] = {
			{		// Teléfono no válido.
				"1234", ArrayIndexOutOfBoundsException.class
			}, {	// Teléfono válido.
				"+34 663475722", null
			}, {	// Teléfono no válido (caracteres no numéricos).
				"blae", ArrayIndexOutOfBoundsException.class
			}, {	// Teléfono no válido.
				"35435", ArrayIndexOutOfBoundsException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templatePhoneValidator((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String password, final String name, final String surname, final String email, final String phone, final String address, final String id, final Class<?> expected) {
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
			c.setPostalAddress(address);
			c.setIdNumber(id);

			//final User saved = this.userService.save(c);
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

	protected void templatePhoneValidator(final String phone, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.userService.phoneValidator(phone);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
