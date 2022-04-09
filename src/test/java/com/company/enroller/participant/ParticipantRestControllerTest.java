package com.company.enroller.participant;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.Optional;

import com.company.enroller.participant.ParticipantRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.company.enroller.participant.Participant;
import com.company.enroller.meeting.MeetingService;
import com.company.enroller.participant.ParticipantService;

@RunWith(SpringRunner.class)
@WebMvcTest(ParticipantRestController.class)
public class ParticipantRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MeetingService meetingService;

	@MockBean
	private ParticipantService participantService;

	@Test
	public void getParticipants() throws Exception {
		Participant participant = new Participant();
		participant.setLogin("testlogin");
		participant.setPassword("testpassword");

		Collection<Participant> allParticipants = singletonList(participant);
		given(participantService.getAll()).willReturn(allParticipants);

		mvc.perform(get("/participants").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].login", is(participant.getLogin())));
	}

	@Test
	public void getParticipantByIdParticipantExist() throws Exception {
		Participant participant = new Participant();
		participant.setLogin("testLogin");
		participant.setPassword("testPassword");

		given(participantService.findByLogin("testLogin")).willReturn(Optional.of(participant));

		mvc.perform(get("/participants/testLogin")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("login",is(participant.getLogin())))
				.andExpect(jsonPath("password", is(participant.getPassword())));

	}

	@Test
	public void getParticipantByIdParticipantNotExist() throws Exception {

		given(participantService.findByLogin("testLogin")).willReturn(Optional.empty());

		mvc.perform(get("/participants/testLogin"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void addCorrectParticipant() throws Exception {

		Participant participant = new Participant();
		participant.setLogin("testlogin");
		participant.setPassword("testpassword");

		String inputJSON = "{\"login\":\"testlogin\", \"password\":\"testpassword\"}";

		given(participantService.findByLogin("testlogin")).willReturn(Optional.empty());
		mvc.perform(post("/participants").content(inputJSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

		given(participantService.findByLogin("testlogin")).willReturn(Optional.of(participant));
		mvc.perform(post("/participants").content(inputJSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());

		verify(participantService, times(2)).findByLogin("testlogin");
		verify(participantService).addParticipant(any());

	}

	@Test
	public void addAlreadyExistedParticipant() throws Exception {
		Participant participant = new Participant();
		participant.setLogin("testlogin");
		participant.setPassword("testpassword");

		String inputJSON = "{\"login\":\"testlogin\", \"password\":\"testpassword\"}";

		given(participantService.findByLogin("testlogin")).willReturn(Optional.of(participant));

		mvc.perform(post("/participants").content(inputJSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());

		verify(participantService).findByLogin("testlogin");
		verify(participantService, never()).addParticipant(any());

	}

	@Test
	public void addNullParticipant() throws Exception {

		String inputJSON = "{}";
		mvc.perform(post("/participants").content(inputJSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

		verify(participantService, never()).addParticipant(any());

	}

	@Test
	public void addParticipantWithoutLogin() throws Exception {

		String inputJSON = "{\"password\":\"testpassword\"}";
		mvc.perform(post("/participants").content(inputJSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

		verify(participantService, never()).addParticipant(any());

	}

	@Test
	public void addParticipantWithEmptyLogin() throws Exception {

		String inputJSON = "{\"login\":\"\",\"password\":\"testpassword\"}";
		mvc.perform(post("/participants").content(inputJSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

		verify(participantService, never()).addParticipant(any());

	}

	@Test
	public void addParticipantWithoutPassword() throws Exception {

		String inputJSON = "{\"login\":\"testlogin\"}";;
		mvc.perform(post("/participants").content(inputJSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

		verify(participantService, never()).addParticipant(any());

	}

	@Test
	public void addParticipantWithEmptyPassword() throws Exception {

		String inputJSON = "{\"login\":\"testlogin\", \"password\":\"\"}";
		mvc.perform(post("/participants").content(inputJSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

		verify(participantService, never()).addParticipant(any());

	}

	@Test
	public void deleteParticipant() throws Exception {
		Participant participant = new Participant();
		participant.setLogin("testlogin");
		participant.setPassword("testpassword");

		when(participantService.findByLogin("testlogin")).thenReturn(Optional.of(participant));

		mvc.perform(delete("/participants/testlogin")).andExpect(status().isNoContent());

		verify(participantService).deleteParticipant(participant);

	}

	@Test
	public void deleteParticipantNotExist() throws Exception {

		when(participantService.findByLogin("testlogin")).thenReturn(Optional.empty());

		mvc.perform(delete("/participants/testlogin")).andExpect(status().isBadRequest());

		verify(participantService, never()).deleteParticipant(any());

	}

	@Test
	public void updateParticipantOK() throws Exception {
		Participant participant = new Participant();
		participant.setLogin("testlogin");
		participant.setPassword("testpassword");

		when(participantService.findByLogin("testlogin")).thenReturn(Optional.of(participant));
		mvc.perform(put("/participants/testlogin?password=newPassword")).andExpect(status().isOk());

		verify(participantService).updateParticipant(participant, "newPassword");

	}

	@Test
	public void updateParticipantNoExist() throws Exception {

		when(participantService.findByLogin("testlogin")).thenReturn(Optional.empty());
		mvc.perform(put("/participants/testlogin?password=newPassword")).andExpect(status().isNotFound());

		verify(participantService, never()).updateParticipant(any(), any());
	}

	@Test
	public void updateParticipantEmptyPassword() throws Exception {

		Participant participant = new Participant();
		participant.setLogin("testlogin");
		participant.setPassword("testpassword");


		when(participantService.findByLogin("testlogin")).thenReturn(Optional.of(participant));
		mvc.perform(put("/participants/testlogin?password=")).andExpect(status().isBadRequest());

		verify(participantService, never()).updateParticipant(any(), any());

	}

	@Test
	public void updateParticipantNoPassword() throws Exception {

		Participant participant = new Participant();
		participant.setLogin("testlogin");
		participant.setPassword("testpassword");

		when(participantService.findByLogin("testlogin")).thenReturn(Optional.of(participant));
		mvc.perform(put("/participants/testlogin")).andExpect(status().isBadRequest());

		verify(participantService, never()).updateParticipant(any(), any());

	}

}


