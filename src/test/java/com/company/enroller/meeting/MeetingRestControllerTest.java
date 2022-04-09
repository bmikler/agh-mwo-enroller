package com.company.enroller.meeting;

import com.company.enroller.participant.ParticipantService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
public class MeetingRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MeetingService meetingService;

    @MockBean
    private ParticipantService participantService;

    @Test
    public void getAllMeetingsUnsorted() throws Exception {
        Meeting firstMeeting = new Meeting("firstMeeting", "firstDescription", "firstDate");
        Meeting secondMeeting = new Meeting("secondMeeting", "secondDescription", "secondDate");

        List<Meeting> allMeetings = List.of(firstMeeting, secondMeeting);

        when(meetingService.getAll()).thenReturn(allMeetings);

        mvc.perform(get("/meetings")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", Matchers.is(firstMeeting.getTitle())))
                .andExpect(jsonPath("$[1].title", is(secondMeeting.getTitle())));

        verify(meetingService, never()).getAllSorted();

    }

    @Test
    public void getAllMeetingsUnsortedEmptyDB() throws Exception {

        List<Meeting> allMeetings = Collections.emptyList();

        when(meetingService.getAll()).thenReturn(allMeetings);

        mvc.perform(get("/meetings")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(meetingService, never()).getAllSorted();

    }

    @Test
    public void getAllMeetingsSorted() throws Exception {
        Meeting firstMeeting = new Meeting("firstMeeting", "firstDescription", "firstDate");
        Meeting secondMeeting = new Meeting("secondMeeting", "secondDescription", "secondDate");

        List<Meeting> allMeetings = List.of(firstMeeting, secondMeeting);

        when(meetingService.getAllSorted()).thenReturn(allMeetings);

        mvc.perform(get("/meetings?sorted=true")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", Matchers.is(firstMeeting.getTitle())))
                .andExpect(jsonPath("$[1].title", is(secondMeeting.getTitle())));

        verify(meetingService, never()).getAll();

    }

    @Test
    public void getAllMeetingsSortedEmptyDB() throws Exception {

        List<Meeting> allMeetings = Collections.emptyList();

        when(meetingService.getAll()).thenReturn(allMeetings);

        mvc.perform(get("/meetings?sorted=true")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(meetingService, never()).getAll();

    }

    @Test
    public void findMeetingByIdMeetingFound() throws Exception {

        Meeting meeting = new Meeting();
        meeting.setId(1L);
        meeting.setTitle("title");
        meeting.setDescription("description");
        meeting.setDate("date");

        when(meetingService.findById(1L)).thenReturn(Optional.of(meeting));

        mvc.perform(get("/meetings/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(meeting.getTitle())))
                .andExpect(jsonPath("$.description", is(meeting.getDescription())))
                .andExpect(jsonPath("$.date", is(meeting.getDate())));

    }

    @Test
    public void findMeetingByIdMeetingNotFound() throws Exception {

        when(meetingService.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(get("/meetings/1")).andExpect(status().isNotFound());

    }


    /*TODO
       search by participant found not found, by text found not found, without paramters error, both parameters error
       add meeting - correct, incomplete
       delete meeting - already exist ot not
       update meeitng - found not found

    */

}