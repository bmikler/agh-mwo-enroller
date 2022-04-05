package com.company.enroller.meeting;

import com.company.enroller.participant.Participant;
import com.company.enroller.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

    @Autowired
    private final MeetingService meetingService;
    private final ParticipantService participantService;

    public MeetingRestController(MeetingService meetingService, ParticipantService participantService) {
        this.meetingService = meetingService;
        this.participantService = participantService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAllMeetings() {
        Collection<Meeting> meetings = meetingService.getAll();
        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetingById(@PathVariable long id) {

        Meeting meeting = meetingService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/participants")
    public ResponseEntity<?> getParticipantsFromMeeting(@PathVariable long id) {

        Meeting meeting = meetingService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Participant> participants = meetingService.getParticipant(meeting);

        return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addMeeting(@RequestBody MeetingRequest meeting) {

        meetingService.addMeeting(meeting);

        return new ResponseEntity<MeetingRequest>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> addParticipantsToMeeting(@PathVariable long id, @RequestBody Participant participant) {

        Meeting meeting = meetingService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting with id" + id + " not found"));

        Participant participantToAdd = participantService.findByLogin(participant.getLogin())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant doesn't exist"));


        meetingService.addParticipant(meeting, participantToAdd);

        return new ResponseEntity<Participant>(participant, HttpStatus.OK);

    }






}
