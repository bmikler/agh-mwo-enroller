package com.company.enroller.meeting;

import com.company.enroller.participant.Participant;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class MeetingMapper {

    public Meeting map(MeetingRequest meetingRequest) {

        return new Meeting(
                meetingRequest.getName(),
                meetingRequest.getDescription(),
                new HashSet<>());
    }

    public MeetingResponse map (Meeting meeting) {
        return new MeetingResponse(
                meeting.getId(),
                meeting.getName(),
                meeting.getDescription(),
                meeting.getParticipants().stream()
                        .map(Participant::getLogin)
                        .collect(Collectors.toSet())
        );
    }

}
