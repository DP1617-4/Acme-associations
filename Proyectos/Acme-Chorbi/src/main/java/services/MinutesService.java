
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MinutesRepository;

@Service
@Transactional
public class MinutesService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MinutesRepository	minutesRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private MeetingService		meetingService;

	@Autowired
	private UserService			userService;


	// Constructors -----------------------------------------------------------

	public MinutesService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Minutes create(final int meetingId) {
		final Minutes result = new Minutes();
		Collection<User> participants = new ArrayList<User>();
		Meeting meeting = meetingService.findOne(meetingId);

		result.setMeeting(meeting);
		result.setParticipants(participants);

		return result;
	}
	public Minutes findOne(final int minutesId) {
		Minutes result;

		result = this.minutesRepository.findOne(minutesId);

		return result;
	}

	public Collection<Minutes> findAll() {
		return this.minutesRepository.findAll();
	}

	public void delete(final Minutes minutes) {

		this.minutesRepository.delete(minutes);

	}

	public void addParticipant(int participantId) {
		User participant = this.userService.findOne(participantId);
		Collection<User> participants = minutes.getParticipants();

		participants.add(participant);
	}

}
