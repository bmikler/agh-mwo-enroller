package com.company.enroller.meeting;

import com.company.enroller.participant.Participant;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Set<String> participants;

}
