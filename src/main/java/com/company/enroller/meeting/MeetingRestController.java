package com.company.enroller.meeting;

import com.company.enroller.participant.Participant;
import com.company.enroller.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;


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
    public ResponseEntity<?> getAllMeetings(@RequestParam(required = false) boolean sorted) {

        if (sorted) {
            return ResponseEntity.ok(meetingService.getAllSorted());
        }

        return ResponseEntity.ok(meetingService.getAll());
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetingById(@PathVariable long id) {

        return ResponseEntity.of(meetingService.findById(id));
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> searchByTitleOrDescription(@RequestParam(required = false) String text,
                                                      @RequestParam(required = false) String participant) {

        if (text != null && participant == null) {
            return ResponseEntity.ok(meetingService.searchByTitleOrDescription(text));
        }

        if (text == null && participant != null) {
            return ResponseEntity.ok(meetingService.searchByParticipant(participant));
        }

        return ResponseEntity.badRequest().build();

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addMeeting(@Valid @RequestBody MeetingRequest meeting) {

        meetingService.addMeeting(meeting);

        return new ResponseEntity<MeetingRequest>(meeting, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMeeting(@PathVariable long id) {

        Meeting meetingToDelete = meetingService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting not found."));

        meetingService.deleteMeeting(meetingToDelete);

        return ResponseEntity.noContent().build();

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMeeting (@PathVariable long id, @Valid @RequestBody MeetingRequest meetingRequest) {

        Meeting meeting = meetingService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting not found."));

        Meeting meetingUpdated = meetingService.update(meeting, meetingRequest);

        return ResponseEntity.ok(meetingUpdated);

    }

    @RequestMapping(value = "{id}/participants", method = RequestMethod.GET)
    public ResponseEntity<?> getAllParticipantsFromMeeting(@PathVariable long id) {

        Meeting meeting = meetingService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting not found."));

        return ResponseEntity.ok(meeting.getParticipants());

    }


    @RequestMapping(value = "/{id}/participants", method = RequestMethod.POST)
    public ResponseEntity<?> addParticipantsToMeeting(@PathVariable long id, @RequestParam String login) {

        Meeting meeting = meetingService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting not found."));

        Participant participantToAdd = participantService.findByLogin(login)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found."));

        if(meeting.getParticipants().contains(participantToAdd)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Participant already assigned to the meeting");
        }

        meetingService.addParticipant(meeting, participantToAdd);

        return ResponseEntity.ok(participantToAdd);

    }

    @RequestMapping(value = "/{id}/participants", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeParticipantFromMeeting(@PathVariable long id, @RequestParam String login) {

        Meeting meeting = meetingService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting not found."));

        Participant participant = participantService.findByLogin(login)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found."));

        if (!meeting.getParticipants().contains(participant)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Participant " + login + " is not assigned to meeting " + id);
        }

        meetingService.removeParticipant(meeting, participant);

        return ResponseEntity.noContent().build();

    }




}
