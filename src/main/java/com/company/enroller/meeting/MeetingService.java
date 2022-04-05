package com.company.enroller.meeting;

import java.util.Collection;

import com.company.enroller.meeting.MeetingMapper;
import com.company.enroller.meeting.MeetingRequest;
import com.company.enroller.persistence.DatabaseConnector;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.company.enroller.meeting.Meeting;

import javax.persistence.NoResultException;

@Component("meetingService")
public class MeetingService {

	private final DatabaseConnector connector;
	private final MeetingMapper mapper;

	public MeetingService(MeetingMapper mapper) {
		this.mapper = mapper;
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

    public Meeting findById(long id) {
		String hql = "FROM Meeting p where p.id =: id";

		Query query = connector.getSession().createQuery(hql);

		query.setParameter("id", id);

		try {
			Meeting meeting = (Meeting) query.getSingleResult();
			return meeting;
		} catch (NoResultException e) {
			return null;
		}

    }

	public void addMeeting(MeetingRequest meeting) {

		Meeting meetingToSave = mapper.map(meeting);

		Session session = connector.getSession();

		Transaction transaction = session.beginTransaction();
		session.save(meetingToSave);
		transaction.commit();


	}
}
