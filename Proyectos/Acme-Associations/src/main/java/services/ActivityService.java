
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ActivityRepository;
import domain.Activity;
import domain.Item;
import domain.User;

@Service
@Transactional
public class ActivityService {

	//managed repository ---------------------------------------
	@Autowired
	private ActivityRepository	activityRepository;


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
		Activity result;
		result = this.activityRepository.save(activity);
		return result;
	}

	public void delete(final Activity activity) {
		this.activityRepository.delete(activity);
	}

	//Auxiliary methods ----------------------------------------
	public void flush() {
		this.activityRepository.flush();
	}

	//Our other bussiness methods ------------------------------
	public void setWinner(final Activity activity, final User user, final Item item) {
		User winner;
		if (item.getItemCondition() != Item.PRIZE)
			item.setItemCondition(Item.PRIZE);
		winner = user;
		activity.setWinner(winner);
		this.save(activity);
	}

	public Collection<Activity> findAllByAssociation(final int associationId) {
		Collection<Activity> result;
		result = this.activityRepository.findAllByAssociation(associationId);

		return result;
	}

}
