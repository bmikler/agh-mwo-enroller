package com.company.enroller.meeting;

public class MeetingMapper {

    public Meeting map(MeetingRequest meetingRequest) {
        return new Meeting(
                meetingRequest.getTitle(),
                meetingRequest.getDescription(),
                meetingRequest.getDate());
    }

}
