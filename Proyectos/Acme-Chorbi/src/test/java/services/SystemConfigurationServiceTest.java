
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.SystemConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SystemConfigurationServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private SystemConfigurationService	sysConService;

	@Autowired
	private AdministratorService		adminService;


	// Tests ---------------------------------------------------------------
	@SuppressWarnings("unchecked")
	@Test
	public void driverModifyingCache() {

		final Collection<String> banners = new ArrayList<String>();
		final String url = "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png";
		banners.add(url);
		final Collection<String> bannersEmpty = null;
		final Collection<String> bannersFull = new ArrayList<String>();
		final Collection<String> bannersWrong = new ArrayList<String>();
		for (int i = 0; i < 20; i++)
			bannersFull.add(url);
		final String urlWrong = "Esto no es un link";
		bannersWrong.add(urlWrong);

		final Double feeBuena = 2.0;
		final Double feeMala = -2.0;

		final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			final Date dateWrong = sdf.parse("10:00:00");
			final Date dateRight = sdf.parse("13:00:00");

			final Object testingData[][] = {
				{	// Modificación correcta: Caché correcta.
					"admin", bannersFull, dateRight, feeBuena, null
				}, { // Modificacion erronea: Cache errónea.
					"admin", bannersFull, dateWrong, feeBuena, IllegalArgumentException.class
				}, { // Modificacion erronea: Banners en null.
					"admin", bannersEmpty, dateRight, feeBuena, ConstraintViolationException.class
				}, { // Modificacion erronea: Cuota errónea.
						//	- An actor who is authenticated as an administrator must be able to:
						//		o Change the fee that is charged to managers and chorbies. (Note that they need not be the same.)
					"admin", bannersFull, dateRight, feeMala, ConstraintViolationException.class
				}
			};
			for (int i = 0; i < testingData.length; i++)
				this.templateModifyingCache((String) testingData[i][0], (Collection<String>) testingData[i][1], (Date) testingData[i][2], (Double) testingData[i][3], (Class<?>) testingData[i][4]);
		} catch (final ParseException e) {
			e.printStackTrace();
		}
	}

	// Templates ----------------------------------------------------------
	protected void templateModifyingCache(final String username, final Collection<String> banners, final Date cacheTime, final Double fee, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final SystemConfiguration sc = this.sysConService.findMain();
			sc.setBanners(banners);
			sc.setCacheTime(cacheTime);
			sc.setFeeChorbi(fee);
			sc.setFeeManager(fee);
			this.sysConService.save(sc);
			this.sysConService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
