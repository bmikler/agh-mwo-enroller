package com.company.enroller.meeting;

import com.company.enroller.participant.ParticipantService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MeetingsParticipantsRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MeetingService meetingService;

    @MockBean
    private ParticipantService participantService;

    /*
    * TODO
    *  get all participants - meeting found and not found
    *  add new participant - meeting found/ not found/ participant found/ not found
    *  delete participant - meeting found/ not found/ meeting contains this participant or not
    *
    * */

}
