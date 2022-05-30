//package com.company.enroller.meeting;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//public class MeetingServiceTest {
//
//    private MeetingService service;
//    private MeetingHibernateRepository repository;
//    private MeetingMapper mapper;
//
//    @Before
//    public void init() {
//        repository = mock(MeetingHibernateRepository.class);
//        mapper = mock(MeetingMapper.class);
//        service = new MeetingService(repository, mapper);
//    }
//
//    @Test
//    public void searchByTitleOrDescriptionTitleFound() {
//        Meeting meeting = new Meeting();
//        meeting.setId(1L);
//        meeting.setTitle("title");
//        meeting.setDescription("description");
//        meeting.setDate("date");
//
//        when(repository.findByTitle("title")).thenReturn(List.of(meeting));
//        when(repository.findByDescription("description")).thenReturn(Collections.emptyList());
//
//        Collection<Meeting> expected = List.of(meeting);
//        Collection<Meeting> actual = service.searchByTitleOrDescription("title");
//
//        assertEquals(expected, actual);
//
//    }
//
//    @Test
//    public void searchByTitleOrDescriptionDescriptionFound() {
//
//        Meeting meeting = new Meeting();
//        meeting.setId(1L);
//        meeting.setTitle("title");
//        meeting.setDescription("description");
//        meeting.setDate("date");
//
//        when(repository.findByTitle("title")).thenReturn(Collections.emptyList());
//        when(repository.findByDescription("description")).thenReturn(List.of(meeting));
//
//        Collection<Meeting> expected = List.of(meeting);
//        Collection<Meeting> actual = service.searchByTitleOrDescription("description");
//
//        assertEquals(expected, actual);
//
//    }
//
//    @Test
//    public void searchByTitleOrDescriptionFoundBoth() {
//
//        Meeting meeting = new Meeting();
//        meeting.setId(1L);
//        meeting.setTitle("some title");
//        meeting.setDescription("some description");
//        meeting.setDate("date");
//
//        when(repository.findByTitle("some")).thenReturn(List.of(meeting));
//        when(repository.findByDescription("some")).thenReturn(List.of(meeting));
//
//        Collection<Meeting> expected = List.of(meeting);
//        Collection<Meeting> actual = service.searchByTitleOrDescription("some");
//
//        assertEquals(expected, actual);
//
//    }
//
//    @Test
//    public void searchByTitleOrDescriptionNothingFound() {
//
//        when(repository.findByTitle("some")).thenReturn(Collections.emptyList());
//        when(repository.findByDescription("some")).thenReturn(Collections.emptyList());
//
//        Collection<Meeting> expected = Collections.emptyList();
//        Collection<Meeting> actual = service.searchByTitleOrDescription("some");
//
//        assertEquals(expected, actual);
//
//
//    }
//
//    @Test
//    public void updateMeeting() {
//        Meeting meeting = new Meeting();
//        meeting.setId(1L);
//        meeting.setTitle("title");
//        meeting.setDescription("description");
//        meeting.setDate("date");
//
//        MeetingRequest meetingRequest = new MeetingRequest();
//        meetingRequest.setName("update");
//        meetingRequest.setDescription("update");
//        meetingRequest.setDate("update");
//
//        Meeting expected = new Meeting();
//        expected.setId(1L);
//        expected.setTitle("update");
//
//        expected.setDescription("update");
//        expected.setDate("update");
//
//        Meeting actual = service.update(meeting, meetingRequest);
//
//        assertEquals(expected, actual);
//        verify(repository).update(expected);
//
//    }
//
//
//}