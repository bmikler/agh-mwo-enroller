package com.company.enroller.meeting;

import java.util.Collection;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

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

		System.out.println(meeting);

		Meeting meetingToSave = mapper.map(meeting);

		System.out.println(meetingToSave);

		repository.save(meetingToSave);


	}
}
