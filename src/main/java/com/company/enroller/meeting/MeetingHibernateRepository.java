package com.company.enroller.meeting;

import com.company.enroller.participant.Participant;
import com.company.enroller.persistence.DatabaseConnector;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.io.Serializable;
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

    public Collection<Meeting> getAllSortedByTitle() {
        String hql = "FROM Meeting m ORDER BY m.title";
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

    public List<Meeting> findMeetingByTitle(String title) {

        String hql = "FROM Meeting m where m.title like :title";

        Query query = connector.getSession().createQuery(hql);

        query.setParameter("title", '%'+title+'%');

        return query.list();

    }

    public List<Meeting> findMeetingByDescription(String description) {
        
        String hql = "FROM Meeting m where m.description like :description";

        Query query = connector.getSession().createQuery(hql);

        query.setParameter("description", '%'+description+'%');

        return query.list();

    }

    public List<Meeting> findMeetingByParticipant(String login) {

        String hql = "SELECT m FROM Meeting m JOIN m.participants p where p.login = :login";
        Query query = connector.getSession().createQuery(hql);

        query.setParameter("login" , login);

        return query.list();

    }

    public List<Participant> getParticipants (Meeting meeting) {
        String hql = "select participants FROM Meeting m where m.id =: id";
        Query query = connector.getSession().createQuery(hql);

        query.setParameter("id", meeting.getId());

        return query.list();

    }

    public Meeting save(Meeting meeting) {
        Session session = connector.getSession();

        Transaction transaction = session.beginTransaction();
        Serializable meetingSaved = session.save(meeting);
        transaction.commit();

        return (Meeting) meetingSaved;

    }

    public void update(Meeting meeting) {
        Session session = connector.getSession();

        Transaction transaction = session.beginTransaction();
        session.merge(meeting);
        transaction.commit();


    }

    public void delete(Meeting meeting) {

        Session session = connector.getSession();

        Transaction transaction = session.beginTransaction();
        session.delete(meeting);
        transaction.commit();

    }
}
