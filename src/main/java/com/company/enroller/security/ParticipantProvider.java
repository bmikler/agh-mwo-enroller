package com.company.enroller.security;

import com.company.enroller.participant.Participant;
import com.company.enroller.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ParticipantProvider implements UserDetailsService {

    @Autowired
    ParticipantService participantService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Participant participant = participantService.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(participant.getLogin(), participant.getPassword(), Collections.emptyList());
    }
}
