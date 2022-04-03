package com.company.enroller.persistence;

import java.util.Collection;
import java.util.Optional;

import org.hibernate.QueryParameterException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Participant;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.NoResultException;
import javax.servlet.http.Part;

@Component("participantService")
public class ParticipantService {

	DatabaseConnector connector;

	public ParticipantService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Participant> getAll() {
		String hql = "FROM Participant";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Participant findByLogin(String login) {
		String hql = "FROM Participant p where p.login =: login";

		Query query = connector.getSession().createQuery(hql);

		query.setParameter("login", login);

		try {
			Participant participant = (Participant) query.getSingleResult();
			return participant;
		} catch (NoResultException e) {
			return null;
		}


	}

	public void addParticipant(Participant participant) {

		Session session = connector.getSession();

		Transaction transaction = session.beginTransaction();
		session.save(participant);
		transaction.commit();

	}

	public void deleteParticipant(Participant participant) {

		Session session = connector.getSession();

		Transaction transaction = session.beginTransaction();
		session.delete(participant);
		transaction.commit();

	}

	public void updateParticipant(Participant participantOld, Participant participantNew) {

		participantOld = participantNew;

		Session session = connector.getSession();

		Transaction transaction = session.beginTransaction();
		session.save(participantOld);
		transaction.commit();

	}

}
