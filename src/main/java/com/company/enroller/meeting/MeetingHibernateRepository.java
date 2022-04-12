package com.company.enroller.meeting;

import com.company.enroller.participant.Participant;
import com.company.enroller.persistence.HibernateRepository;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class MeetingHibernateRepository extends HibernateRepository {


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
            return Optional.ofNullable(meeting);
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

    public List<Meeting> findByTitle(String title) {

        String hql = "FROM Meeting m where m.title like :title";

        Query query = connector.getSession().createQuery(hql);

        query.setParameter("title", '%'+title+'%');

        return query.list();

    }

    public List<Meeting> findByDescription(String description) {

        String hql = "FROM Meeting m where m.description like :description";

        Query query = connector.getSession().createQuery(hql);

        query.setParameter("description", '%'+description+'%');

        return query.list();

    }

    public List<Meeting> findByParticipant(String participantLogin) {

        String hql = "SELECT m FROM Meeting m JOIN m.participants p where p.login = :login";
        Query query = connector.getSession().createQuery(hql);

        query.setParameter("login" , participantLogin);

        return query.list();

    }


}
