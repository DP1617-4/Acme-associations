
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PlaceRepository;
import domain.Activity;
import domain.Place;

@Service
@Transactional
public class PlaceService {

	//managed repository ---------------------------------------
	@Autowired
	private PlaceRepository	placeRepository;

	@Autowired
	private ActivityService	activityService;


	//supporting services --------------------------------------

	// Constructors --------------------------------------------
	public PlaceService() {
		super();
	}

	//Basic CRUD methods ---------------------------------------
	public Place create() {
		Place created;
		created = new Place();
		return created;
	}

	public Place findOne(final int placeId) {
		Place retrieved;
		retrieved = this.placeRepository.findOne(placeId);
		return retrieved;
	}

	public Collection<Place> findAll() {
		Collection<Place> result;
		result = this.placeRepository.findAll();
		return result;
	}

	public Place save(final Place place, final Activity activity) {
		Assert.notNull(place);
		if (place.getLongitude() != null)
			Assert.isTrue(place.getLatitude() != null, "place.longitude.notDefined");
		if (place.getLatitude() != null)
			Assert.isTrue(place.getLongitude() != null, "place.latitude.notDefined");
		Place result;
		result = this.placeRepository.save(place);
		if (activity.getPlace() == null)
			this.activityService.setPlace(result, activity);
		return result;
	}

	public void delete(final Place place) {
		this.placeRepository.delete(place);
	}

	//Auxiliary methods ----------------------------------------
	public void flush() {
		this.placeRepository.flush();
	}

	//Our other bussiness methods ------------------------------

}
