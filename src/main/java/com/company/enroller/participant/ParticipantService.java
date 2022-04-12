package com.company.enroller.participant;

import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {

	private final ParticipantHibernateRepository repository;

	public ParticipantService(ParticipantHibernateRepository repository) {
		this.repository = repository;
	}

	public Collection<Participant> getAll() {
		return repository.readAll(Participant.class);
	}

	public Optional<Participant> findByLogin(String login) {

		return repository.findByLogin(login);

	}

	public void addParticipant(Participant participant) {

		repository.create(participant);

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
