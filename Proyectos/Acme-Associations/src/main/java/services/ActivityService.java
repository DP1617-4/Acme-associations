
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
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
	
	@Autowired
	private RolesService		rolesService;


	// Constructors --------------------------------------------
	public ActivityService() {
		super();
	}

	//Basic CRUD methods ---------------------------------------
	public Activity create(final Association association) {
		Activity created;
		created = new Activity();
		created.setAssociation(association);
		created.setAttendants(new ArrayList<User>());
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
		rolesService.checkCollaboratorPrincipal(activity.getAssociation());
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
		Assert.isTrue(activity.getAttendants().contains(user));
		rolesService.checkCollaboratorPrincipal(activity.getAssociation());
		if (item != null && item.getItemCondition() != Item.PRIZE)
			item.setItemCondition(Item.PRIZE);
		winner = user;
		activity.setWinner(winner);
		activityRepository.save(activity);
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

	public Collection<Activity> activeActivitiesWithMostUsers() {
		Collection<Activity> result;

		result = this.activityRepository.activeActivitiesWithMostUsers();
		return result;
	}

	public void addParticipant(final User user, final Activity activity) {
		rolesService.checkAssociatePrincipal(activity.getAssociation());
		if(!activity.getPublicActivity()){
			Assert.notNull(rolesService.findRolesByUserAssociation(user, activity.getAssociation()));
		}
		Collection<User> attendants;
		attendants = activity.getAttendants();
		if (!attendants.contains(user))
			attendants.add(user);
		else if (attendants.contains(user))
			attendants.remove(user);
		activity.setAttendants(attendants);
		this.activityRepository.save(activity);
	}

	public Activity findMostAttendedByAssociation(final Association association) {

		Activity activity = null;
		final List<Activity> activities = new ArrayList<Activity>(this.activityRepository.findMostAttendedByAssociation(association.getId()));
		if (!activities.isEmpty())
			activity = activities.get(0);

		return activity;

	}

	public void setPlace(final Place place, final Activity activity) {
		activity.setPlace(place);
		this.save(activity);

	}

	public Collection<Activity> findAllNotFinished() {

		return this.activityRepository.findAllNotFinished();
	}

}
