package com.company.enroller.participant;

import java.util.Collection;
import java.util.Optional;

import com.company.enroller.persistence.DatabaseConnector;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {

	private final ParticipantHibernateRepository repository;

	public ParticipantService(ParticipantHibernateRepository repository) {
		this.repository = repository;
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

	public Participant updateParticipant(Participant participant, String newPassword) {

		participant.setPassword(newPassword);
		repository.update(participant);

		return participant;

	}

}
