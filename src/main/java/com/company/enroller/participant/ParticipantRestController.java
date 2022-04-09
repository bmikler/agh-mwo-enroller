package com.company.enroller.participant;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/participants")
public class ParticipantRestController {

	@Autowired
	ParticipantService participantService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants() {

		return ResponseEntity.ok(participantService.getAll());

	}

	@RequestMapping(value = "/{login}", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipantById(@PathVariable("login") String login) {

		return ResponseEntity.of(participantService.findByLogin(login));
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> registerParticipant(@Valid @RequestBody Participant participant) {

		participantService.findByLogin(participant.getLogin()).ifPresent(p -> {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Participant with login " + participant.getLogin() + " already exist.");
		});

		participantService.addParticipant(participant);

		return new ResponseEntity<Participant>(participant, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/{login}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteParticipant(@PathVariable String login) {

		Participant participantFound = participantService.findByLogin(login)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Participant not found."));

		participantService.deleteParticipant(participantFound);

		return ResponseEntity.noContent().build();

	}


	@RequestMapping(value = "/{login}", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateParticipantPassword(@PathVariable String login,@RequestParam  String password) {

		if (password.isBlank()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password can`t be blank.");
		}

		Participant participant = participantService.findByLogin(login).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found."));

		Participant participantUpdated = participantService.updateParticipant(participant, password);

		return ResponseEntity.ok(participantUpdated);

	}


}
