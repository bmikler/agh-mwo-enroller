package com.company.enroller.meeting;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.company.enroller.participant.Participant;
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

    public Optional<Meeting> findById(long id) {
		return repository.findById(id);
    }

	public void addMeeting(MeetingRequest meeting) {

		Meeting meetingToSave = mapper.map(meeting);
		repository.save(meetingToSave);


	}

	public List<Participant> getParticipant(Meeting meeting) {

		return repository.getParticipants(meeting);

	}

	public void addParticipant(Meeting meeting, Participant participant) {

		meeting.addParticipant(participant);
		repository.update(meeting);

	}
}
