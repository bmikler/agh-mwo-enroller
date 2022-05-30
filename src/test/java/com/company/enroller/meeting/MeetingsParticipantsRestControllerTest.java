package com.company.enroller.meeting;

import com.company.enroller.participant.Participant;
import com.company.enroller.participant.ParticipantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MeetingsParticipantsRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MeetingService meetingService;

    @MockBean
    private ParticipantService participantService;

    @Test
    public void getAllParticipantOK() throws Exception {

        Meeting meeting = new Meeting();
        meeting.setId(1L);
        meeting.setTitle("title");
        meeting.setDescription("description");
        meeting.setDate("date");

        Participant participant = new Participant();
        participant.setLogin("login");
        participant.setPassword("password");

        meeting.addParticipant(participant);

        when(meetingService.findById(1L)).thenReturn(Optional.of(meeting));

        mvc.perform(get("/api/meetings/1/participants")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].login", is(participant.getLogin())));


    }

    @Test
    public void getAllParticipantMeetingNotFound() throws Exception {

        given(meetingService.findById(1L)).willReturn(Optional.empty());

        mvc.perform(get("/api/meetings/1/participants"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void addParticipantOK() throws Exception {
        Meeting meeting = new Meeting();
        meeting.setId(1L);
        meeting.setTitle("title");
        meeting.setDescription("description");
        meeting.setDate("date");

        Participant participant = new Participant();
        participant.setLogin("login");
        participant.setPassword("password");

        when(meetingService.findById(1L)).thenReturn(Optional.of(meeting));
        when((participantService.findByLogin("login"))).thenReturn(Optional.of(participant));

        mvc.perform(post("/api/meetings/1/participants?login=login")).andExpect(status().isOk())
                .andExpect(jsonPath("$.login", is(participant.getLogin())));

        verify(meetingService).addParticipant(meeting, participant);
    }

    @Test
    public void addParticipantMeetingNotFound() throws Exception {

        when(meetingService.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(post("/api/meetings/1/participants?login=login")).andExpect(status().isNotFound());

        verify(meetingService, never()).addParticipant(any(), any());
    }

    @Test
    public void addParticipantParticipantNotFound() throws Exception {

        Meeting meeting = new Meeting();
        meeting.setId(1L);
        meeting.setTitle("title");
        meeting.setDescription("description");
        meeting.setDate("date");

        when(meetingService.findById(1L)).thenReturn(Optional.of(meeting));
        when(participantService.findByLogin("login")).thenReturn(Optional.empty());

        mvc.perform(post("/api/meetings/1/participants?login=login")).andExpect(status().isNotFound());

        verify(meetingService, never()).addParticipant(any(), any());

    }

    @Test
    public void addParticipantParticipantAlreadyAssigned() throws Exception {
        Meeting meeting = new Meeting();
        meeting.setId(1L);
        meeting.setTitle("title");
        meeting.setDescription("description");
        meeting.setDate("date");

        Participant participant = new Participant();
        participant.setLogin("login");
        participant.setPassword("password");

        meeting.addParticipant(participant);

        when(meetingService.findById(1L)).thenReturn(Optional.of(meeting));
        when(participantService.findByLogin("login")).thenReturn(Optional.of(participant));

        mvc.perform(post("/api/meetings/1/participants?login=login")).andExpect(status().isBadRequest());


        verify(meetingService, never()).addParticipant(meeting, participant);

    }

    @Test
    public void removeParticipantOK() throws Exception {
        Meeting meeting = new Meeting();
        meeting.setId(1L);
        meeting.setTitle("title");
        meeting.setDescription("description");
        meeting.setDate("date");

        Participant participant = new Participant();
        participant.setLogin("login");
        participant.setPassword("password");

        meeting.addParticipant(participant);

        when(meetingService.findById(1L)).thenReturn(Optional.of(meeting));
        when(participantService.findByLogin("login")).thenReturn(Optional.of(participant));

        mvc.perform(delete("/api/meetings/1/participants?login=login")).andExpect(status().isNoContent());

        verify(meetingService).removeParticipant(meeting, participant);

    }

    @Test
    public void removeParticipantMeetingNotFound() throws Exception {

        when(meetingService.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(delete("/api/meetings/1/participants?login=login")).andExpect(status().isNotFound());

        verify(meetingService, never()).addParticipant(any(), any());
    }

    @Test
    public void removeParticipantParticipantNotFound() throws Exception {
        Meeting meeting = new Meeting();
        meeting.setId(1L);
        meeting.setTitle("title");
        meeting.setDescription("description");
        meeting.setDate("date");

        when(meetingService.findById(1L)).thenReturn(Optional.of(meeting));
        when(participantService.findByLogin("login")).thenReturn(Optional.empty());

        mvc.perform(delete("/api/meetings/1/participants?login=login")).andExpect(status().isNotFound());

        verify(meetingService, never()).removeParticipant(eq(meeting), any());

    }

    @Test
    public void removeParticipantParticipantNotSignedToMeeting() throws Exception {
        Meeting meeting = new Meeting();
        meeting.setId(1L);
        meeting.setTitle("title");
        meeting.setDescription("description");
        meeting.setDate("date");

        Participant participant = new Participant();
        participant.setLogin("login");
        participant.setPassword("password");

        when(meetingService.findById(1L)).thenReturn(Optional.of(meeting));
        when((participantService.findByLogin("login"))).thenReturn(Optional.of(participant));

        mvc.perform(post("/api/meetings/1/participants?participant=login")).andExpect(status().isBadRequest());

        verify(meetingService, never()).addParticipant(meeting, participant);


    }


}
