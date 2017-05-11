
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ActivityRepository;

@Service
@Transactional
public class ActivityService {

	//managed repository ---------------------------------------
	@Autowired
	private ActivityRepository	activityRepository;


	//supporting services --------------------------------------

	// Constructors --------------------------------------------
	public ActivityService() {
		super();
	}

	//Basic CRUD methods ---------------------------------------
	public Activity create() {
		Activity created;
		created = new Activity();
		return created;
	}

	public Activity findOne(final int activityId) {
		Activity retrieved;
		retrieved = this.activityRepository.findOne(activityId);
		return retrieved;
	}

	public Collection<Activity> findAll() {
		Collection<Activity> result;
		result = this.activityRepository.findAll();
		return result;
	}

	public Activity save(final Activity activity) {

	}

	public void delete(final Activity activity) {
		this.activityRepository.delete(activity);
	}

	//Auxiliary methods ----------------------------------------

	//Our other bussiness methods ------------------------------

}
