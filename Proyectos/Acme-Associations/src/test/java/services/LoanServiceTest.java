
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
import domain.Item;
import domain.Loan;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class LoanServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private LoanService	loanService;

	@Autowired
	private ItemService	itemService;

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
			{		// Creación correcta de una Association.
				"user1", this.fechaValida, this.fechaFutura, this.fechaValida, "item1", "user1", "user2", null
			}, {	// Creación errónea de una Association: name vacío.
				"user1", null, this.fechaFutura, this.fechaValida, "item1", "user1", "user2", ConstraintViolationException.class
			}, {	// Creación errónea de una Association: creationDate null.
				"user1", this.fechaFutura, this.fechaFutura, this.fechaValida, "item1", "user1", "user2", ConstraintViolationException.class
			}, {	// Creación errónea de una Association: creationDate futuro.
				"user1", this.fechaValida, null, this.fechaValida, "item1", "user1", "user2", ConstraintViolationException.class
			}, {	// Creación errónea de una Association: creationDate null.
				"user1", this.fechaValida, this.fechaFutura, null, "item1", "user1", "user2", ConstraintViolationException.class
			}, {	// Creación errónea de una Association: creationDate null.
				"user1", this.fechaValida, this.fechaFutura, this.fechaFutura, null, "user1", "user2", NullPointerException.class
			}, {		// Creación correcta de una Association.
				"user1", this.fechaValida, this.fechaFutura, this.fechaValida, "item100", "user1", "user2", NumberFormatException.class
			}, {		// Creación correcta de una Association.
				"user1", this.fechaValida, this.fechaFutura, this.fechaValida, "item1", null, "user2", NullPointerException.class
			}, {		// Creación correcta de una Association.
				"user1", this.fechaValida, this.fechaFutura, this.fechaValida, "item1", "user100", "user2", NumberFormatException.class
			}, {		// Creación correcta de una Association.
				"user1", this.fechaValida, this.fechaFutura, this.fechaValida, "item1", "user1", null, NullPointerException.class
			}, {		// Creación correcta de una Association.
				"user1", this.fechaValida, this.fechaFutura, this.fechaValida, "item1", "user1", "user200", NumberFormatException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (Date) testingData[i][1], (Date) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (Class<?>) testingData[i][7]);
	}
	@Test
	public void driverDisplay() {
		final Object testingData[][] = {
			{		// Display correcto de una association ya creado y logueado como tal. 
				"user1", "loan1", null
			}, {	// Display correcto de un user distinto al que está logueado.
				null, "loan1", null
			}, {		// Intento de mostrar una asociacion que no existe
				"user1", "loan100", NumberFormatException.class
			}, {		// Intento de mostrar una asociacion que no existe
				"user1", null, NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String user, final Date startDate, final Date expectedDate, final Date finalDate, final String item, final String lender, final String borrower, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);

			final Loan l = this.loanService.create();

			final Item i = this.itemService.findOne(this.extract(item));
			final User lend = this.userService.findOne(this.extract(lender));
			final User borr = this.userService.findOne(this.extract(borrower));

			l.setStartDate(startDate);
			l.setExpectedDate(expectedDate);
			l.setFinalDate(finalDate);
			l.setItem(i);
			l.setLender(lend);
			l.setBorrower(borr);

			final Loan saved = this.loanService.save(l);
			final Loan res = this.loanService.end(saved);
			this.loanService.delete(res);
			this.unauthenticate();
			this.itemService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateDisplaying(final String username, final String loanId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			this.loanService.findOne(this.extract(loanId));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
