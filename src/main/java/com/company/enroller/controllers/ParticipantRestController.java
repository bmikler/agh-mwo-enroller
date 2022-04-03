package com.company.enroller.controllers;

import java.nio.file.Path;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/participants")
public class ParticipantRestController {

	@Autowired
	ParticipantService participantService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants() {
		Collection<Participant> participants = participantService.getAll();
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipantById(@PathVariable("id") String login) {

		Participant participant = participantService.findByLogin(login);

		if (participant == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Participant>(participant, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> registerParticipant(@RequestBody Participant participant) {

		Participant participantFound = participantService.findByLogin(participant.getLogin());

		if (participantFound != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}

		participantService.addParticipant(participant);

		return new ResponseEntity<Participant>(participant, HttpStatus.OK);

	}

	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteParticipant(@RequestBody Participant participant) {

		Participant participantFound = participantService.findByLogin(participant.getLogin());
		if (participantFound == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		participantService.deleteParticipant(participantFound);

		return new ResponseEntity<Participant>(participant, HttpStatus.OK);

	}


	@RequestMapping(value = "/{login}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateParticipant(@PathVariable String login, @RequestBody Participant participant) {

		Participant participantFound = participantService.findByLogin(login);

		if (participantFound == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		participantService.updateParticipant(participantFound, participant);

		return new ResponseEntity<Participant>(participant, HttpStatus.OK);

	}


}
