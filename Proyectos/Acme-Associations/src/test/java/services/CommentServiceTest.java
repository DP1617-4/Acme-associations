
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private CommentService	likesService;

	@Autowired
	private UserService		userService;


	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	//- An actor who is authenticated must be able to:
	//	o Post a comment on another actor, on an offer, or a request
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Comment.
				"user1", "correcto", 2, null
			}, {	// Creación correcta de un Comment: Text vacío.
				"user3", "", 2, null
			}, {	// Creación erronea de un Comment: Segundo like a una segunda persona, el mensaje que de error en la consola es esperado.
				"user4", "", 2, DataIntegrityViolationException.class
			}, {	// Creación errónea de un Comment: sin autenticar.
				null, "correcto", 2, IllegalArgumentException.class
			}, {	// Creación incorrecta de un Comment: a sí mismo
				"user2", "correcto", 2, IllegalArgumentException.class
			}, {	// Creación incorrecta de un Comment: estrellas fuera de rango
				"user1", "correcto", 4, DataIntegrityViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	//An actor who is authenticated as a user must be able to:
	//	o Unlike a user he has liked

	@Test
	public void driverUnlike() {
		final Object testingData[][] = {
			{		// Borrado correcta de un Comment.
				"user1", "user1", null
			}, {	// Borrado erronea de un Comment: distinto usuario.
				"user1", "user2", IllegalArgumentException.class
			}, {	// Borrado errónea de un Comment: sin autenticar.
				"user1", null, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateUnlike((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String text, final Integer stars, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			this.userService.findOne(this.extract("user2"));
			final Comment c = this.likesService.create(null);
			

			this.likesService.save(c);
			this.likesService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateUnlike(final String username, final String username2, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {

			this.authenticate(username);
			this.userService.findOne(this.extract("user2"));
			final Comment comment = this.likesService.create(null);
			this.likesService.save(comment);
			this.authenticate(username2);
			//			this.likesService.delete(result);
			this.unauthenticate();

			this.likesService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
