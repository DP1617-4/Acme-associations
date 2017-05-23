
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ActivityRepository;
import domain.Activity;
import domain.Association;
import domain.Item;
import domain.Place;
import domain.User;

@Service
@Transactional
public class ActivityService {

	//managed repository ---------------------------------------
	@Autowired
	private ActivityRepository	activityRepository;

	@Autowired
	private Validator			validator;


	// Constructors --------------------------------------------
	public ActivityService() {
		super();
	}

	//Basic CRUD methods ---------------------------------------
	public Activity create(final Association association) {
		Activity created;
		created = new Activity();
		created.setAssociation(association);
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

	public Activity reconstruct(final Activity activity, final Association association, final BindingResult binding) {
		Activity result;

		if (activity.getId() == 0) {
			result = this.create(association);

			if (activity.getPlace().getLatitude() == null) {
				if (activity.getPlace().getLongitude() != null)
					binding.rejectValue("place.latitude", "javax.validation.constraints.NotNull.message");
			} else if (activity.getPlace().getLongitude() == null)
				if (activity.getPlace().getLatitude() != null)
					binding.rejectValue("place.longitude", "javax.validation.constraints.NotNull.message");

			result.setName(activity.getName());
			result.setStartMoment(activity.getStartMoment());
			result.setDescription(activity.getDescription());
			result.setEndMoment(activity.getEndMoment());
			result.setMaximumAttendants(activity.getMaximumAttendants());
			result.setAssociation(activity.getAssociation());
			result.setAttendants(activity.getAttendants());
			result.setPublicActivity(activity.getPublicActivity());
			result.setWinner(activity.getWinner());
			result.setItem(activity.getItem());
			result.setPlace(activity.getPlace());
		} else {
			result = this.activityRepository.findOne(activity.getId());

			result.setName(activity.getName());
			result.setStartMoment(activity.getStartMoment());
			result.setDescription(activity.getDescription());
			result.setEndMoment(activity.getEndMoment());
			result.setMaximumAttendants(activity.getMaximumAttendants());
			result.setAssociation(activity.getAssociation());
			result.setAttendants(activity.getAttendants());
			result.setPublicActivity(activity.getPublicActivity());
			result.setWinner(activity.getWinner());
			result.setItem(activity.getItem());
			result.setPlace(activity.getPlace());

			this.validator.validate(result, binding);
		}

		return result;
	}

	public void addParticipant(final User user, final Activity activity) {
		Collection<User> attendants;
		attendants = activity.getAttendants();
		attendants.add(user);
		activity.setAttendants(attendants);
	}

	public Activity findMostAttendedByAssociation(Association association) {

		return this.activityRepository.findMostAttendedByAssociation(association.getId());

	}

	public void setPlace(final Place place, final Activity activity) {
		activity.setPlace(place);
		this.save(activity);

	}

}
