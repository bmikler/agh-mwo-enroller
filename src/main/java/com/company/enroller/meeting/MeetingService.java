package com.company.enroller.meeting;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import com.company.enroller.participant.Participant;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MeetingService {

	private final MeetingHibernateRepository repository;
	private final MeetingMapper mapper;

	public MeetingService(MeetingHibernateRepository repository, MeetingMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public Collection<MeetingResponse> getAll() {

		return repository.getAll().stream()
				.map(mapper::map).toList();
	}

	public Collection<MeetingResponse> getAllSorted() {

		return repository.getAllSortedByTitle()
				.stream().map(mapper::map).toList();

	}

    public Optional<Meeting> findById(long id) {
		return repository.findById(id);
    }

	public Collection<MeetingResponse> searchByTitleOrDescription(String text) {

		List<Meeting> meetingsByTitle = repository.findByTitle(text);
		List<Meeting> meetingsByDescription = repository.findByDescription(text);

		return Stream.of(meetingsByTitle, meetingsByDescription)
				.flatMap(Collection::stream)
				.distinct()
				.map(mapper::map)
				.toList();

	}

	public Collection<MeetingResponse> searchByParticipant(String participant) {

		return repository.findByParticipant(participant)
				.stream().map(mapper::map).toList();

	}

	public void addMeeting(MeetingRequest meeting) {

		Meeting meetingToSave = mapper.map(meeting);
		repository.create(meetingToSave);

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

		meeting.setName(meetingRequest.getName());
		meeting.setDescription(meetingRequest.getDescription());;

		repository.update(meeting);

		return meeting;

	}
}
