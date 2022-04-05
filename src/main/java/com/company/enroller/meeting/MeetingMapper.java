package com.company.enroller.meeting;

import org.springframework.stereotype.Component;

@Component
public class MeetingMapper {

    public Meeting map(MeetingRequest meetingRequest) {

        return new Meeting(
                meetingRequest.getTitle(),
                meetingRequest.getDescription(),
                meetingRequest.getDate());
    }

}
