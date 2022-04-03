package com.company.enroller.persistence;

import java.util.Collection;

import com.company.enroller.model.Participant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

import javax.persistence.NoResultException;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;

	public MeetingService() {
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

	public void addMeeting(Meeting meeting) {

		Session session = connector.getSession();

		Transaction transaction = session.beginTransaction();
		session.save(meeting);
		transaction.commit();


	}
}
