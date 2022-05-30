package com.company.enroller.participant;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

public class ParticipantServiceTest {

    private ParticipantService service;
    private ParticipantHibernateRepository repository;
    private PasswordEncoder passwordEncoder;

    @Before
    public void init() {
        passwordEncoder = mock(PasswordEncoder.class);
        repository = mock(ParticipantHibernateRepository.class);
        service = new ParticipantService(repository, passwordEncoder);
    }

    @Test
    public void updateParticipant() {

        Participant participant = new Participant();
        participant.setLogin("testLogin");
        participant.setPassword("testPassword");

        String newPassword = "newPassword";

        Participant participantExpected = new Participant();
        participantExpected.setLogin("testLogin");
        participantExpected.setPassword(newPassword);

        service.updateParticipant(participant, newPassword);

        verify(repository).update(participantExpected);

    }

}