package com.company.enroller.participant;

import com.company.enroller.meeting.Meeting;
import com.company.enroller.persistence.DatabaseConnector;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class ParticipantHibernateRepository {

    private DatabaseConnector connector;

    public ParticipantHibernateRepository() {
        connector = DatabaseConnector.getInstance();
    }

    public Collection<Participant> getAll() {
        String hql = "FROM Participant";
        Query query = connector.getSession().createQuery(hql);
        return query.list();
    }

    public Optional<Participant> findByLogin(String login) {
        String hql = "FROM Participant p where p.login =: login";

        Query query = connector.getSession().createQuery(hql);

        query.setParameter("login", login);

        try {
            Participant participant = (Participant) query.getSingleResult();
            return Optional.of(participant);
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

    public void save(Participant participant) {

        Session session = connector.getSession();

        Transaction transaction = session.beginTransaction();
        session.save(participant);
        transaction.commit();

    }

    public void delete(Participant participant) {

        Session session = connector.getSession();

        Transaction transaction = session.beginTransaction();
        session.delete(participant);
        transaction.commit();

    }

    public void update(Participant participant, String newPassword) {

        participant.setPassword(newPassword);

        Session session = connector.getSession();

        Transaction transaction = session.beginTransaction();
        session.update(participant);
        transaction.commit();

    }

}
