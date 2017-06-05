
package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Activity;
import domain.Place;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PlaceServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private PlaceService		placeService;

	@Autowired
	private ActivityService		activityService;


	// Tests ---------------------------------------------------------------
	@Test
	public void driverCreateDelete() {
		final Object testingData[][] = {
			{		// Comprobacion correcta: nuevo place
				"user1","activity1","address", 45.0, 45.0, null
			}, {		// Comprobacion correcta: latitud incorrecta
				"user1","activity1","address", 1000.0, 45.0, ConstraintViolationException.class
			}, {		// Comprobacion correcta: latitud nula
				"user1","activity1","address", null, 45.0, IllegalArgumentException.class
			}, {		// Comprobacion correcta: longitud incorrecta
				"user1","activity1","address", 45.0, 1000.0, ConstraintViolationException.class
			}, {		// Comprobacion correcta: longitud nula
				"user1","activity1","address", 45.0, null, IllegalArgumentException.class
			}, {		// Comprobacion correcta: address null
				"user1","activity1",null, 45.0, 45.0, ConstraintViolationException.class
			}, {		// Comprobacion incorrecta: address vacio
				"user1","activity1","", 45.0, 45.0, ConstraintViolationException.class
			}, {		// Comprobacion incorrecta: sin loguear
				null,"activity1","address", 45.0, 45.0, NullPointerException.class
			}, {		// Comprobacion incorrecta: Activity con place
				"user1","activity3","address", 45.0, 45.0, ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++){
			this.templateCreateDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3],  (Double) testingData[i][4], (Class<?>) testingData[i][5]);
		}
	}

	// Templates ----------------------------------------------------------
	protected void templateCreateDelete(final String username, final String activity, final String address, final Double latitude, final Double longitude, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Activity a = activityService.findOne(this.extract(activity));
			Place place = this.placeService.create();
			place.setAddress(address);
			place.setLatitude(latitude);
			place.setLongitude(longitude);
			place = this.placeService.save(place, a);
			this.placeService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
