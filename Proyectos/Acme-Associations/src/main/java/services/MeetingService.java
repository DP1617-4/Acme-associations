
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MeetingRepository;
import domain.Association;
import domain.Meeting;

@Service
@Transactional
public class MeetingService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MeetingRepository	meetingRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AssociationService	associationService;

	@Autowired
	private RolesService		rolesService;


	// Constructors -----------------------------------------------------------

	public MeetingService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Meeting create(final int associationId) {
		final Meeting result = new Meeting();

		final Association association = this.associationService.findOne(associationId);

		this.rolesService.checkManagerPrincipal(association);
		result.setAssociation(association);

		return result;
	}
	public Meeting findOne(final int meetingId) {

		Assert.isTrue(meetingId != 0);
		Meeting result;

		result = this.meetingRepository.findOne(meetingId);

		return result;
	}

	public Collection<Meeting> findAll() {

		Collection<Meeting> result;
		result = this.meetingRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public void delete(final Meeting meeting) {

		Assert.notNull(meeting);
		Assert.isTrue(meeting.getId() != 0);
		Assert.isTrue(this.meetingRepository.exists(meeting.getId()));
		this.meetingRepository.delete(meeting);

	}

	public Meeting save(final Meeting meeting) {
		Assert.notNull(meeting);
		Meeting result;

		result = this.meetingRepository.save(meeting);
		return result;
	}

	public Collection<Meeting> findAllByAssociation(final Association association) {
		Collection<Meeting> result;
		result = this.meetingRepository.findAllByAssociation(association.getId());
		return result;
	}

	public void flush() {
		this.meetingRepository.flush();

	}
}
