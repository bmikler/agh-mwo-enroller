package com.company.enroller.meeting;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MeetingServiceTest {

    private MeetingService meetingService;
    private MeetingHibernateRepository repository;
    private MeetingMapper mapper;


    @Before
    public void init() {
        repository = mock(MeetingHibernateRepository.class);
        mapper = mock(MeetingMapper.class);
        meetingService = new MeetingService(repository,mapper);
    }

    @Test
    public void getAllSorted() {

        Meeting meetingA = new Meeting("a", "a", "a");
        Meeting meetingB = new Meeting("b", "b","b");
        Meeting meetingZ = new Meeting("z", "z", "z");

        Collection<Meeting> meetings = List.of(meetingB, meetingZ, meetingA);

        when(repository.getAll()).thenReturn(meetings);

        Collection<Meeting> expectedMeetingList = List.of(meetingA, meetingB, meetingZ);
        Collection<Meeting> actualMeetingList = meetingService.getAllSorted();

        assertEquals(expectedMeetingList, actualMeetingList);

    }


}