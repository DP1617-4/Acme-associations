
package services;


import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
	private CommentService			commentService;

	@Autowired
	private UserService				userService;

	@Autowired
	private AssociationService		associationService;

	@Autowired
	private MeetingService			meetingService;

	@Autowired
	private MinutesService			minutesService;

	@Autowired
	private ItemService				itemService;


	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	//- An actor who is authenticated must be able to:
	//	o Post a comment on another association, meeting, minute and item
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Comment: Manager.
				"user1", "association1", "correcto", "correcto", null
			}, {		// Creación correcta de un Comment: Collaborator.
				"user3", "association1", "correcto", "correcto", null
			}, {		// Creación correcta de un Comment: Associated.
				"user8", "association1", "correcto", "correcto", null
			}, {		// Creación erronea de un Comment: No logueado.
				null, "association1", "correcto", "correcto", IllegalArgumentException.class
			}, {		// Creación correcta de un Comment: Meeting.
				"user1", "meeting1", "correcto", "correcto", null
			}, {		// Creación correcta de un Comment: Minutes.
				"user1", "minutes1", "correcto", "correcto", null
			}, {		// Creación correcta de un Comment: Item.
				"user1", "item1", "correcto", "correcto", null
			}, {		// Creación erronea de un Comment: Sin texto.
				"user1", "item1", null, "correcto", ConstraintViolationException.class
			}, {		// Creación correcta de un Comment: Sin titulo.
				"user1", "item1", "correcto", null, ConstraintViolationException.class
			}, {		// Creación correcta de un Comment: Texto vacio.
				"user1", "item1", "", "correcto", ConstraintViolationException.class
			}, {		// Creación correcta de un Comment: Titulo vacio.
				"user1", "item1", "correcto", "", ConstraintViolationException.class
			}, {		// Creación correcta de un Comment: Sin commentable.
				"user1", null, "correcto", "correcto", NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++){
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
		}
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String commentable, final String text, final String title, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			this.userService.findOne(this.extract("user2"));
			int commentableId = 0;
			if(commentable.contains("association")){
				commentableId = associationService.findOne(this.extract(commentable)).getId();
			}else{
				if(commentable.contains("meeting")){
					commentableId = meetingService.findOne(this.extract(commentable)).getId();
				}else{
					if(commentable.contains("minutes")){
						commentableId = minutesService.findOne(this.extract(commentable)).getId();
					}else{
						if(commentable.contains("item")){
							commentableId = itemService.findOne(this.extract(commentable)).getId();
						}
					}
				}
			}
			final Comment c = this.commentService.create(commentableId);
			c.setText(text);
			c.setTitle(title);

			this.commentService.save(c);
			this.commentService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
