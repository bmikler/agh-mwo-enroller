package com.company.enroller.meeting;

import com.company.enroller.participant.Participant;
import com.company.enroller.persistence.DatabaseConnector;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class MeetingHibernateRepository {

    private DatabaseConnector connector;

    public MeetingHibernateRepository() {
        this.connector = DatabaseConnector.getInstance();
    }


    public Collection<Meeting> getAll() {
        String hql = "FROM Meeting";
        Query query = connector.getSession().createQuery(hql);
        return query.list();
    }

    public Optional<Meeting> findById(long id) {
        String hql = "FROM Meeting p where p.id =: id";

        Query query = connector.getSession().createQuery(hql);

        query.setParameter("id", id);

        try {
            Meeting meeting = (Meeting) query.getSingleResult();
            return Optional.of(meeting);
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }


    public List<Participant> getParticipants (Meeting meeting) {
        String hql = "select participants FROM Meeting m where m.id =: id";
        Query query = connector.getSession().createQuery(hql);

        query.setParameter("id", meeting.getId());

        return query.list();

    }


    public void save(Meeting meeting) {
        Session session = connector.getSession();

        Transaction transaction = session.beginTransaction();
        session.persist(meeting);
        transaction.commit();

    }

    public void update(Meeting meeting) {
        Session session = connector.getSession();

        Transaction transaction = session.beginTransaction();
        session.merge(meeting);
        transaction.commit();


    }

}
