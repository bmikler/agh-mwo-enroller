package com.company.enroller.meeting;

import com.company.enroller.participant.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingResponse {

    private long id;
    private String name;
    private String description;
    private Set<String> participants;

}
