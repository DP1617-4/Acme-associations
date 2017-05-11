
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MeetingRepository;

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
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public MeetingService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Meeting create(final int associationId) {
		final Meeting result = new Meeting();

		Association association = associationService.findOne(associationId);
		result.setAssociation(association);

		return result;
	}
	public Meeting findOne(final int meetingId) {
		Meeting result;

		result = this.meetingRepository.findOne(meetingId);

		return result;
	}

	public Collection<Meeting> findAll() {
		return this.meetingRepository.findAll();
	}

	public void delete(final Meeting meeting) {

		this.meetingRepository.delete(meeting);

	}
}
