
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MinutesRepository;
import domain.Meeting;
import domain.Minutes;
import domain.User;

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
		final Collection<User> participants = new ArrayList<User>();
		final Meeting meeting = this.meetingService.findOne(meetingId);

		result.setMeeting(meeting);
		result.setUsers(participants);

		return result;
	}

	public Minutes findOne(final int minutesId) {

		Assert.isTrue(minutesId != 0);
		Minutes result;

		result = this.minutesRepository.findOne(minutesId);

		return result;
	}

	public Collection<Minutes> findAll() {

		Collection<Minutes> result;
		result = this.minutesRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public void delete(final Minutes minutes) {

		Assert.notNull(minutes);
		Assert.isTrue(minutes.getId() != 0);
		Assert.isTrue(this.minutesRepository.exists(minutes.getId()));
		this.minutesRepository.delete(minutes);

	}

	public Minutes save(final Minutes minutes) {
		Assert.notNull(minutes);
		Minutes result;
		Assert.isTrue(this.findOneByMeeting(minutes.getMeeting()) == null);
		result = this.minutesRepository.save(minutes);
		return result;
	}

	public void addParticipant(final int participantId, final int minutesId) {
		Minutes minutes;
		final User participant = this.userService.findOne(participantId);
		minutes = this.minutesRepository.findOne(minutesId);
		final Collection<User> participants = minutes.getUsers();

		participants.add(participant);
		this.save(minutes);
	}

	public Minutes findOneByMeeting(final Meeting meeting) {
		Minutes result;
		result = this.minutesRepository.findOneByMeeting(meeting.getId());
		return result;
	}

}
