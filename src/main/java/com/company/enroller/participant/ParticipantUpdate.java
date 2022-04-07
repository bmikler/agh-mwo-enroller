package com.company.enroller.participant;

import javax.validation.constraints.NotEmpty;

public class ParticipantUpdate {
    private String password;

    @NotEmpty
    public String getPassword() {
        return password;
    }
}

