
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Association;
import domain.Roles;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RolesServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private RolesService			rolesService;
	
	@Autowired
	private UserService				userService;
	
	@Autowired
	private AssociationService		associationService;


	// Tests ---------------------------------------------------------------
	@Test
	public void driverCreateDeleteRol() {
		final Object testingData[][] = {
			{		// Crear role correcto: añadir associado
				"user1", "user9", "association1", "ASSOCIATE", null
			}, {	// Crear role correcto: añadir colacborador
				"user1", "user9", "association1", "COLLABORATOR", null
			}, {	// Crear role incorrecto: autodegradar Manager
				"user1", "user1", "association1", "ASSOCIATE", IllegalArgumentException.class
			}, {	// Crear role correcto: Degradar collaborador a asociado
				"user1", "user3", "association1", "ASSOCIATE", null
			}, {	// Crear role correcto: Ascender asociado a colaborador
				"user1", "user8", "association1", "COLLABORATOR", null
			}, {	// Crear role incorrecto: asignar manager
				"user3", "user9", "association1", "MANAGER", IllegalArgumentException.class
			}, {	// Crear role incorrecto: asignar colaborador sin loguear
				null, "user9", "association1", "COLLABORATOR", IllegalArgumentException.class
			}, {	// Crear role incorrecto: asignar asociado sin loguear
				null, "user9", "association1", "ASSOCIATE", IllegalArgumentException.class
			}, {	// Crear role incorrecto: asignar manager sin loguear
				null, "user9", "association1", "MANAGER",  IllegalArgumentException.class
			}, {	// Crear role correcto: traspasar poderes
				"user1", "user9", "association1", "MANAGER", null
			}
		};
		for (int i = 0; i < testingData.length; i++){
			this.templateCreateDeleteRol((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}
	
	@Test
	public void driverCheckAssociate() {
		final Object testingData[][] = {
			{		// Crear role correcto: añadir associado
				"user1", "user1", "association1", null
			}, {		// Crear role correcto: añadir associado
				"user1", "user3", "association1", null
			}, {		// Crear role correcto: añadir associado
				"user1", "user8", "association1", null
			}, {		// Crear role correcto: añadir associado
				"user1", "user9", "association1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCheckAssociate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	
	@Test
	public void driverCheckAssociatePrincipal() {
		final Object testingData[][] = {
			{		// Crear role correcto: añadir associado
				"user1", "association1", null
			}, {		// Crear role correcto: añadir associado
				"user3", "association1", null
			}, {		// Crear role correcto: añadir associado
				"user8", "association1", null
			}, {		// Crear role correcto: añadir associado
				"user9", "association1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCheckAssociatePrincipal((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	@Test
	public void driverCheckCollaborator() {
		final Object testingData[][] = {
			{		// Crear role correcto: añadir associado
				"user1", "user1", "association1", null
			}, {		// Crear role correcto: añadir associado
				"user1", "user3", "association1", null
			}, {		// Crear role correcto: añadir associado
				"user1", "user8", "association1", IllegalArgumentException.class
			}, {		// Crear role correcto: añadir associado
				"user1", "user9", "association1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCheckCollaborator((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	
	@Test
	public void driverCheckCollaboratorPrincipal() {
		final Object testingData[][] = {
			{		// Crear role correcto: añadir associado
				"user1", "association1", null
			}, {		// Crear role correcto: añadir associado
				"user3", "association1", null
			}, {		// Crear role correcto: añadir associado
				"user8", "association1", IllegalArgumentException.class
			}, {		// Crear role correcto: añadir associado
				"user9", "association1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCheckCollaboratorPrincipal((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	@Test
	public void driverCheckManager() {
		final Object testingData[][] = {
			{		// Crear role correcto: añadir associado
				"user1", "user1", "association1", null
			}, {		// Crear role correcto: añadir associado
				"user1", "user3", "association1", IllegalArgumentException.class
			}, {		// Crear role correcto: añadir associado
				"user1", "user8", "association1", IllegalArgumentException.class
			}, {		// Crear role correcto: añadir associado
				"user1", "user9", "association1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCheckManager((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	
	@Test
	public void driverCheckManagerPrincipal() {
		final Object testingData[][] = {
			{		// Crear role correcto: añadir associado
				"user1", "association1", null
			}, {		// Crear role correcto: añadir associado
				"user3", "association1", IllegalArgumentException.class
			}, {		// Crear role correcto: añadir associado
				"user8", "association1", IllegalArgumentException.class
			}, {		// Crear role correcto: añadir associado
				"user9", "association1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCheckManagerPrincipal((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreateDeleteRol(final String loggued, final String username, final String association, final String roleType, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(loggued);
			final User user = userService.findOne(this.extract(username));
			final Association a = associationService.findOne(this.extract(association));
			Roles role = rolesService.assignRoles(user, a, roleType);
			role = rolesService.save(role);
			rolesService.delete(role);
			this.unauthenticate();
			rolesService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void templateCheckAssociate(final String loggued, final String username, final String association, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(loggued);
			final User u = userService.findOne(this.extract(username));
			final Association a = associationService.findOne(this.extract(association));
			rolesService.checkAssociate(u, a);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void templateCheckAssociatePrincipal(final String loggued, final String association, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(loggued);
			final Association a = associationService.findOne(this.extract(association));
			rolesService.checkAssociatePrincipal(a);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void templateCheckCollaborator(final String loggued, final String username, final String association, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(loggued);
			final User u = userService.findOne(this.extract(username));
			final Association a = associationService.findOne(this.extract(association));
			rolesService.checkCollaborator(u, a);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void templateCheckCollaboratorPrincipal(final String loggued, final String association, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(loggued);
			final Association a = associationService.findOne(this.extract(association));
			rolesService.checkCollaboratorPrincipal(a);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void templateCheckManager(final String loggued, final String username, final String association, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(loggued);
			final User u = userService.findOne(this.extract(username));
			final Association a = associationService.findOne(this.extract(association));
			rolesService.checkManager(u, a);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void templateCheckManagerPrincipal(final String loggued, final String association, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(loggued);
			final Association a = associationService.findOne(this.extract(association));
			rolesService.checkManagerPrincipal(a);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
