package com.company.enroller.participant;

import java.util.Collection;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantService {

	private final ParticipantHibernateRepository repository;
	private final PasswordEncoder passwordEncoder;

	public Collection<Participant> getAll() {
		return repository.getAll();
	}

	public Optional<Participant> findByLogin(String login) {

		return repository.findByLogin(login);

	}

	public void addParticipant(Participant participant) {

		hashPassword(participant);
		repository.create(participant);

	}

	private void hashPassword(Participant participant) {
		participant.setPassword(passwordEncoder.encode(participant.getPassword()));
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
