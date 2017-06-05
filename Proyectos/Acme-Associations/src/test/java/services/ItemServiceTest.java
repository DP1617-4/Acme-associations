
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
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Item;
import domain.Section;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ItemServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private ItemService		itemService;

	@Autowired
	private SectionService	sectionService;

	Calendar				calendarValida	= new GregorianCalendar(1995, 12, 14);
	Date					fechaValida		= this.calendarValida.getTime();

	Calendar				calendarFutura	= new GregorianCalendar(2020, 12, 14);
	Date					fechaFutura		= this.calendarFutura.getTime();


	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Item
				"user1", "item", "GOOD", "description", "http://www.imagen.com.mx/assets/img/imagen_share.png", "section1", null
			}, {	// Creación errónea de un Item: name vacío.
				"user1", "", "GOOD", "description", "http://www.imagen.com.mx/assets/img/imagen_share.png", "section1", ConstraintViolationException.class
			}, {	// Creación errónea de un Item: condition rara.
				"user1", "item", "blae", "description", "http://www.imagen.com.mx/assets/img/imagen_share.png", "section1", ConstraintViolationException.class
			}, {	//Creación errónea de un Item: condition vacía.
				"user1", "item", "", "description", "http://www.imagen.com.mx/assets/img/imagen_share.png", "section1", ConstraintViolationException.class
			}, {	// Creación errónea de un Item: description vacía.
				"user1", "item", "GOOD", "", "http://www.imagen.com.mx/assets/img/imagen_share.png", "section1", ConstraintViolationException.class
			}, {		// Creación errónea de un Item: picture sin formato URL.
				"user1", "item", "GOOD", "description", "blae", "section1", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	@Test
	public void driverDisplay() {
		final Object testingData[][] = {
			{		// Display correcto de un Item y logueado como user. 
				"user1", "item1", null
			}, {	// Display correcto de un Item y sin estar logueado.
				null, "item1", null
			}, {		// Intento de mostrar un Item que no existe
				"user1", "item100", NumberFormatException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverLoanable() {
		final Object testingData[][] = {
			{		// Comprobación correcta de que el Item es loanable. 
				"user1", "item1", null
			}, {	// Comprobación correcta de que el Item es loanable sin haberse autenticado.
				null, "item1", null
			}, {	// Comprobación de que el Item no es loanable.
				"user1", "item6", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateLoanable((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverIsLoaned() {
		final Object testingData[][] = {
			{		// comprobación de que un item no está loaned logueándose como un user.
				"user1", "item1", IllegalArgumentException.class
			}, {	// comprobación de que un item no está loaned sin haberse logueado.
				null, "item1", IllegalArgumentException.class
			}, {	// comprobación de que un item no está loaned logueándose como un admin.
				"admin", "item1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateisLoaned((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverChangeCondition() {
		final Object testingData[][] = {
			{		// Cambio correcto de condition de un Item, logueado como manager de la Association del Item.
				"user1", "MODERATE", "item1", null
			}, {	// Cambio erroneo de condition de un Item, sin estar logueado.
				null, "MODERATE", "item1", IllegalArgumentException.class
			}, {	// Cambio erroneo de condition de un Item, estando logueado como admin.
				"admin", "MODERATE", "item1", IllegalArgumentException.class
			}, {		// Cambio erroneo de condition de un Item que no existe.
				"user1", "MODERATE", "item100", NumberFormatException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCondition((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String user, final String name, final String itemCondition, final String description, final String picture, final String sectionId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);
			final Section section = this.sectionService.findOne(this.extract(sectionId));
			final Item i = this.itemService.create(section);

			i.setName(name);
			i.setItemCondition(itemCondition);
			i.setDescription(description);
			i.setPicture(picture);

			final Item saved = this.itemService.save(i);
			this.itemService.delete(saved);
			this.unauthenticate();
			this.itemService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateDisplaying(final String username, final String itemId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			this.itemService.findOne(this.extract(itemId));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateLoanable(final String username, final String itemId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Item i = this.itemService.findOne(this.extract(itemId));
			Assert.isTrue(this.itemService.isLoanable(i));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateisLoaned(final String username, final String itemId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Item i = this.itemService.findOne(this.extract(itemId));
			Assert.isTrue(this.itemService.isLoanedByPrincipal(i));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateCondition(final String username, final String condition, final String itemId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Item i = this.itemService.findOne(this.extract(itemId));
			this.itemService.changeCondition(i, condition);
			this.unauthenticate();
			this.itemService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
