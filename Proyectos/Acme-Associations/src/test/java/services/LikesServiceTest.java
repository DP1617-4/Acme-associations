
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Chorbi;
import domain.Likes;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class LikesServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private LikesService	likesService;

	@Autowired
	private ChorbiService	chorbiService;


	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	//- An actor who is authenticated must be able to:
	//	o Post a comment on another actor, on an offer, or a request
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Likes.
				"chorbi1", "correcto", 2, null
			}, {	// Creación correcta de un Likes: Text vacío.
				"chorbi3", "", 2, null
			}, {	// Creación erronea de un Likes: Segundo like a una segunda persona, el mensaje que de error en la consola es esperado.
				"chorbi4", "", 2, DataIntegrityViolationException.class
			}, {	// Creación errónea de un Likes: sin autenticar.
				null, "correcto", 2, IllegalArgumentException.class
			}, {	// Creación incorrecta de un Likes: a sí mismo
				"chorbi2", "correcto", 2, IllegalArgumentException.class
			}, {	// Creación incorrecta de un Likes: estrellas fuera de rango
				"chorbi1", "correcto", 4, DataIntegrityViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	//An actor who is authenticated as a chorbi must be able to:
	//	o Unlike a user he has liked

	@Test
	public void driverUnlike() {
		final Object testingData[][] = {
			{		// Borrado correcta de un Likes.
				"chorbi1", "chorbi1", null
			}, {	// Borrado erronea de un Likes: distinto usuario.
				"chorbi1", "chorbi2", IllegalArgumentException.class
			}, {	// Borrado errónea de un Likes: sin autenticar.
				"chorbi1", null, IllegalArgumentException.class
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
			final Chorbi chorbi = this.chorbiService.findOne(this.extract("chorbi2"));
			final Likes c = this.likesService.create(chorbi);
			c.setComment(text);
			c.setStars(stars);

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
			final Chorbi chorbi = this.chorbiService.findOne(this.extract("chorbi2"));
			final Likes comment = this.likesService.create(chorbi);
			final Likes result = this.likesService.save(comment);
			this.authenticate(username2);
			this.likesService.delete(result);
			this.unauthenticate();

			this.likesService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
