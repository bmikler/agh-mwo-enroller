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
    public ResponseEntity<?> getAllMeetings(@RequestParam(required = false) boolean sorted) {

        Collection<Meeting> meetings;

        if (sorted) {
            meetings = meetingService.getAllSorted();
        } else {
            meetings = meetingService.getAll();
        }

        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetingById(@PathVariable long id) {

        Meeting meeting = meetingService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/search-by-text", method = RequestMethod.GET)
    public ResponseEntity<?> findByTitleOrDescription(@RequestParam String text) {

        Collection<Meeting> meetings = meetingService.searchByTitleOrDescription(text);

        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }

    @RequestMapping(value = "/search-by-participant", method = RequestMethod.GET)
    public ResponseEntity<?> findByParticipant(@RequestParam String participantName) {

        Collection<Meeting> meetings = meetingService.searchByParticipant(participantName);

        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addMeeting(@RequestBody MeetingRequest meeting) {

        meetingService.addMeeting(meeting);

        return new ResponseEntity<MeetingRequest>(meeting, HttpStatus.OK);
    }


    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMeeting(@RequestParam long id) {

        Meeting meetingToDelete = meetingService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting with id" + id + " not found"));

        meetingService.deleteMeeting(meetingToDelete);

        return ResponseEntity.noContent().build();

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMeeting (@PathVariable long id, @RequestBody MeetingRequest meetingRequest) {

        Meeting meeting = meetingService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meeting with id" + id + " not found"));

        Meeting meetingUpdated = meetingService.update(meeting, meetingRequest);

        return new ResponseEntity<Meeting>(meetingUpdated, HttpStatus.OK);

    }

    @RequestMapping(value = "{id}/participants", method = RequestMethod.GET)
    public ResponseEntity<?> getParticipantsFromMeeting(@PathVariable long id) {

        Meeting meeting = meetingService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Participant> participants = meetingService.getParticipant(meeting);

        return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);

    }


    @RequestMapping(value = "/{id}/participants", method = RequestMethod.POST)
    public ResponseEntity<?> addParticipantsToMeeting(@PathVariable long id, @RequestParam String participant) {

        Meeting meeting = meetingService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Participant participantToAdd = participantService.findByLogin(participant)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        meetingService.addParticipant(meeting, participantToAdd);

        return new ResponseEntity<Participant>(participantToAdd, HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}/participants", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeParticipantFromMeeting(@PathVariable long id, @RequestBody Participant participant) {

        Meeting meeting = meetingService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!meeting.getParticipants().contains(participant)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        meetingService.removeParticipant(meeting, participant);

        return ResponseEntity.noContent().build();

    }




}
