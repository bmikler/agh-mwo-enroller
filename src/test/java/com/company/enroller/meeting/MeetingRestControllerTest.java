package com.company.enroller.meeting;

import com.company.enroller.participant.Participant;
import com.company.enroller.participant.ParticipantService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        mvc.perform(get("/api/meetings")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(firstMeeting.getTitle())))
                .andExpect(jsonPath("$[1].title", is(secondMeeting.getTitle())));

        verify(meetingService, never()).getAllSorted();

    }

    @Test
    public void getAllMeetingsUnsortedEmptyDB() throws Exception {

        List<Meeting> allMeetings = Collections.emptyList();

        when(meetingService.getAll()).thenReturn(allMeetings);

        mvc.perform(get("/api/meetings")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(meetingService, never()).getAllSorted();

    }

    @Test
    public void getAllMeetingsSorted() throws Exception {
        Meeting firstMeeting = new Meeting("firstMeeting", "firstDescription", "firstDate");
        Meeting secondMeeting = new Meeting("secondMeeting", "secondDescription", "secondDate");

        List<Meeting> allMeetings = List.of(firstMeeting, secondMeeting);

        when(meetingService.getAllSorted()).thenReturn(allMeetings);

        mvc.perform(get("/api/meetings?sorted=true")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(firstMeeting.getTitle())))
                .andExpect(jsonPath("$[1].title", is(secondMeeting.getTitle())));

        verify(meetingService, never()).getAll();

    }

    @Test
    public void getAllMeetingsSortedEmptyDB() throws Exception {

        List<Meeting> allMeetings = Collections.emptyList();

        when(meetingService.getAll()).thenReturn(allMeetings);

        mvc.perform(get("/api/meetings?sorted=true")).andExpect(status().isOk())
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

        mvc.perform(get("/api/meetings/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(meeting.getTitle())))
                .andExpect(jsonPath("$.description", is(meeting.getDescription())))
                .andExpect(jsonPath("$.date", is(meeting.getDate())));

    }

    @Test
    public void findMeetingByIdMeetingNotFound() throws Exception {

        when(meetingService.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/meetings/1")).andExpect(status().isNotFound());

    }


    @Test
    public void searchByTextOK() throws Exception {
        Meeting meeting1 = new Meeting();
        meeting1.setId(1L);
        meeting1.setTitle("title");
        meeting1.setDescription("description");
        meeting1.setDate("date");

        Meeting meeting2 = new Meeting();
        meeting2.setId(2L);
        meeting2.setTitle("some title");
        meeting2.setDescription("some description");
        meeting2.setDate("some date");

        when(meetingService.searchByTitleOrDescription("title")).thenReturn(List.of(meeting1, meeting2));

        mvc.perform(get("/api/meetings/search?text=title")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(meeting1.getTitle())))
                .andExpect(jsonPath("$[1].title", is(meeting2.getTitle())));

        verify(meetingService, never()).searchByParticipant(any());
    }

    @Test
    public void searchByTextNothingFound() throws Exception {
        when(meetingService.searchByTitleOrDescription("title")).thenReturn(Collections.emptyList());

        mvc.perform(get("/api/meetings/search?text=title")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(meetingService, never()).searchByParticipant(any());
    }

    @Test
    public void searchByParticipantOK() throws Exception {
        Meeting meeting1 = new Meeting();
        meeting1.setId(1L);
        meeting1.setTitle("title");
        meeting1.setDescription("description");
        meeting1.setDate("date");

        Meeting meeting2 = new Meeting();
        meeting2.setId(2L);
        meeting2.setTitle("some title");
        meeting2.setDescription("some description");
        meeting2.setDate("some date");

        Participant participant = new Participant();
        participant.setLogin("login");
        participant.setPassword("password");

        meeting1.addParticipant(participant);
        meeting2.addParticipant(participant);

        when(meetingService.searchByParticipant("login")).thenReturn(List.of(meeting1, meeting2));

        mvc.perform(get("/api/meetings/search?participant=login")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(meeting1.getTitle())))
                .andExpect(jsonPath("$[1].title", is(meeting2.getTitle())));

        verify(meetingService, never()).searchByTitleOrDescription(any());
    }


    @Test
    public void searchWithoutParameters() throws Exception {

        mvc.perform(get("/api/meetings/search")).andExpect(status().isBadRequest());

    }

    @Test
    public void searchWithBothParameters() throws Exception {

        mvc.perform(get("/api/meetings/search?text=test&&participant=test"))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void addMeetingOK() throws Exception {

        MeetingRequest meeting = new MeetingRequest("title", "description", "date");

        String inputJSON = "{\"title\":\"title\", \"description\":\"description\", \"date\":\"date\"}";

        mvc.perform(post("/api/meetings").content(inputJSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.title", is(meeting.getTitle())));

    }

    @Test
    public void addMeetingEmptyInput() throws Exception {

        String inputJson = "{}";

        mvc.perform(post("/api/meetings").content(inputJson)).andExpect(status().isUnsupportedMediaType());

        verify(meetingService, never()).addMeeting(any());
    }

    @Test
    public void addMeetingEmptyTitle() throws Exception {

        String inputJson = "{\"title\":\"\", \"description\":\"description\", \"date\":\"date\"}";

        mvc.perform(post("/api/meetings").content(inputJson)).andExpect(status().isUnsupportedMediaType());

        verify(meetingService, never()).addMeeting(any());
    }

    @Test
    public void addMeetingEmptyDescription() throws Exception {

        String inputJson = "{\"title\":\"title\", \"description\":\"\", \"date\":\"date\"}";

        mvc.perform(post("/api/meetings").content(inputJson)).andExpect(status().isUnsupportedMediaType());

        verify(meetingService, never()).addMeeting(any());
    }

    @Test
    public void addMeetingEmptyDate() throws Exception {

        String inputJson = "{\"title\":\"title\", \"description\":\"description\", \"date\":\"\"}";

        mvc.perform(post("/api/meetings").content(inputJson)).andExpect(status().isUnsupportedMediaType());

        verify(meetingService, never()).addMeeting(any());
    }

    @Test
    public void deleteMeetingOK() throws Exception {
        Meeting meeting = new Meeting();
        meeting.setId(1L);
        meeting.setTitle("title");
        meeting.setDescription("description");
        meeting.setDate("date");

        when(meetingService.findById(1L)).thenReturn(Optional.of(meeting));

        mvc.perform(delete("/api/meetings/1")).andExpect(status().isNoContent());

        verify(meetingService).deleteMeeting(meeting);

    }

    @Test
    public void deleteMeetingMeetingNotFound() throws Exception {
        when(meetingService.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(delete("/api/meetings/1")).andExpect(status().isNotFound());

        verify(meetingService, never()).deleteMeeting(any());


    }

    @Test
    public void updateMeetingOK() throws Exception {
        Meeting meeting = new Meeting();
        meeting.setId(1L);
        meeting.setTitle("title");
        meeting.setDescription("description");
        meeting.setDate("date");

        MeetingRequest meetingRequest = new MeetingRequest();
        meetingRequest.setTitle("update");
        meetingRequest.setDescription("update");
        meetingRequest.setDate("update");

        String inputJSON = "{\"title\":\"update\", \"description\":\"update\", \"date\":\"update\"}";

        Meeting meetingUpdated = new Meeting();
        meetingUpdated.setId(1L);
        meetingUpdated.setTitle("update");
        meetingUpdated.setDescription("update");
        meetingUpdated.setDate("update");

        when(meetingService.findById(1L)).thenReturn(Optional.of(meeting));
        when(meetingService.update(meeting, meetingRequest)).thenReturn(meetingUpdated);

        mvc.perform(put("/api/meetings/1").content(inputJSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is(meetingUpdated.getTitle())))
                .andExpect(jsonPath("$.description", is(meetingUpdated.getDescription())))
                .andExpect(jsonPath("$.date", is(meetingUpdated.getDate())));

    }

    @Test
    public void updateMeetingMeetingNotFound() throws Exception {

        String inputJSON = "{\"title\":\"title\", \"description\":\"description\", \"date\":\"date\"}";

        when(meetingService.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(put("/api/meetings/1").content(inputJSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(meetingService, never()).update(any(), any());

    }


}