
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdminServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;


	// Tests ---------------------------------------------------------------
	@Test
	public void driverCheckAdmin() {
		final Object testingData[][] = {
			{		// Comprobacion correcta: username con sus folders.
				"admin", null
			}, {	// Comprobacion erronea: sin loguear.
				"", IllegalArgumentException.class
			}, {	// Comprobacion erronea: Manager
				"user1", IllegalArgumentException.class
			}, {	// Comprobacion erronea: Colaborador
				"user3", IllegalArgumentException.class
			}, {	// Comprobacion erronea: Asociado.
				"user8", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCheckAdmin((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCheckAdmin(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			this.administratorService.checkAdministrator();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
