package com.company.enroller.participant;

import java.util.Collection;
import java.util.Optional;

import com.company.enroller.persistence.DatabaseConnector;
import org.springframework.stereotype.Component;

@Component("participantService")
public class ParticipantService {

	private final DatabaseConnector connector;
	private final ParticipantHibernateRepository repository;

	public ParticipantService(ParticipantHibernateRepository repository) {
		this.repository = repository;
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Participant> getAll() {
		return repository.getAll();
	}

	public Optional<Participant> findByLogin(String login) {

		return repository.findByLogin(login);

	}

	public void addParticipant(Participant participant) {

		repository.save(participant);

	}

	public void deleteParticipant(Participant participant) {

		repository.delete(participant);

	}

	public void updateParticipant(Participant participant, String newPassword) {

		repository.update(participant, newPassword);

	}

}
