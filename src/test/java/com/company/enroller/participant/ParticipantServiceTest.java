package com.company.enroller.participant;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ParticipantServiceTest {

    private ParticipantService service;
    private ParticipantHibernateRepository repository;

    @Before
    public void init() {
        repository = mock(ParticipantHibernateRepository.class);
        service = new ParticipantService(repository);
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