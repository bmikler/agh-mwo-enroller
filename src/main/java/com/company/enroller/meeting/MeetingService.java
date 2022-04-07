package com.company.enroller.meeting;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.company.enroller.participant.Participant;
import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Service;

@Service
public class MeetingService {

	private final MeetingHibernateRepository repository;
	private final MeetingMapper mapper;

	public MeetingService(MeetingHibernateRepository repository, MeetingMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public Collection<Meeting> getAll() {
		return repository.getAll();
	}

	public Collection<Meeting> getAllSorted() {

		return repository.getAll()
				.stream()
				.sorted(Comparator.comparing(Meeting::getTitle))
				.toList();

	}

    public Optional<Meeting> findById(long id) {
		return repository.findById(id);
    }

	public Collection<Meeting> searchByTitleOrDescription(String text) {

		List<Meeting> meetingsByTitle = repository.findMeetingByTitleContainsText(text);
		List<Meeting> meetingsByDescription = repository.findMeetingByDescriptionContainsText(text);

		return ListUtils.union(meetingsByTitle, meetingsByDescription);

	}

	public Collection<Meeting> searchByParticipant(String participant) {

		return repository.findMeetingByParticipant(participant);

	}

	public Meeting addMeeting(MeetingRequest meeting) {

		Meeting meetingToSave = mapper.map(meeting);
		return repository.save(meetingToSave);

	}

	public List<Participant> getParticipant(Meeting meeting) {

		return repository.getParticipants(meeting);

	}

	public void addParticipant(Meeting meeting, Participant participant) {

		meeting.addParticipant(participant);
		repository.update(meeting);

	}

	public void removeParticipant(Meeting meeting, Participant participant) {

		meeting.removeParticipant(participant);
		repository.update(meeting);

	}

	public void deleteMeeting(Meeting meeting) {

		repository.delete(meeting);
	}

	public Meeting update(Meeting meeting, MeetingRequest meetingRequest) {

		meeting.setTitle(meetingRequest.getTitle());
		meeting.setDescription(meetingRequest.getDescription());
		meeting.setDate(meetingRequest.getDate());

		repository.update(meeting);

		return meeting;

	}
}
