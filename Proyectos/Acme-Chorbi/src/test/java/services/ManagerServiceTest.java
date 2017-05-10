
package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ManagerServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private ManagerService	managerService;


	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	//TODO DataIntegrity error a solucionar.
	@Test
	public void driverCreationRight() {
		final Object testingData[][] = {
			{		// Creación correcta de un Manager.
				"managerTest1", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "equisdejajajaxdxdxd", "43321", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreationDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreationWrong1() {
		final Object testingData[][] = {
			{	// Creación errónea de un Manager: title vacío.
				"managerTest2", "correcto", "", "correcto", "correcto@bien.com", "1234", "equisdejajajaxdxdxd", "43321", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreationDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreationWrong2() {
		final Object testingData[][] = {
			{	// Creación errónea de un Manager: description vacío.
				"managerTest3", "correcto", "correcto", "", "correcto@bien.com", "1234", "equisdejajajaxdxdxd", "43321", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreationDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreationWrong3() {
		final Object testingData[][] = {
			{	// Creación errónea de un Manager: picture que no es url.
				"", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "equisdejajajaxdxdxd", "43321", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreationDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreationWrong4() {
		final Object testingData[][] = {
			{	// Creación errónea de un Manager: moment nulo vacío.
				"managerTest4", "", "correcto", "correcto", "correcto@bien.com", "1234", "equisdejajajaxdxdxd", "43321", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreationDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreationWrong5() {
		final Object testingData[][] = {
			{	// Creación errónea de un Manager: numberSeat negativo.
				"managerTest5", "correcto", "correcto", "correcto", "", "1234", "equisdejajajaxdxdxd", "43321", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreationDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreationWrong6() {
		final Object testingData[][] = {
			{	// Creación errónea de un Manager: no es un manager el logueado.
				"managerTest6", "correcto", "correcto", "correcto", "correcto@bien.com", "", "equisdejajajaxdxdxd", "43321", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreationDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreationWrong7() {
		final Object testingData[][] = {
			{		// Creación correcta de un Manager.
				"managerTest7", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "", "43321", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreationDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreationWrong8() {
		final Object testingData[][] = {
			{		// Creación correcta de un Manager.
				"managerTest8", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", "equisdejajajaxdxdxd", "", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreationDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreationWrong9() {
		final Object testingData[][] = {
			{		// Creación correcta de un Manager.
				"managerTest9", "co", "correcto", "correcto", "correcto@bien.com", "1234", "equisdejajajaxdxdxd", "43321", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreationDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreationWrong10() {
		final Object testingData[][] = {
			{		// Creación correcta de un Manager.
				"managerTest10", "correcto", "correcto", "correcto", "ajjj", "1234", "equisdejajajaxdxdxd", "43321", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreationDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	@Test
	public void driverCreationWrong11() {
		final Object testingData[][] = {
			{		// Creación correcta de un Manager.
				"managerTest11", "correcto", "correcto", "correcto", "correcto@bien.com", "A", "equisdejajajaxdxdxd", "43321", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreationDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}
	// Templates ----------------------------------------------------------
	private void templateCreationDelete(final String username, final String password, final String name, final String surname, final String email, final String phone, final String companyName, final String VATNumber, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			final Manager m = this.managerService.create();

			m.getUserAccount().setUsername(username);
			m.getUserAccount().setPassword(password);
			m.setName(name);
			m.setSurname(surname);
			m.setEmail(email);
			m.setPhoneNumber(phone);

			m.setCompanyName(companyName);
			m.setVATNumber(VATNumber);
			this.managerService.save(m);
			this.managerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
