package com.company.enroller.participant;

import com.company.enroller.meeting.Meeting;
import com.company.enroller.persistence.HibernateRepository;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
public class ParticipantHibernateRepository extends HibernateRepository {

    public List<Participant> getAll() {
        return connector.getSession().createQuery("SELECT p FROM Participant p", Participant.class).getResultList();
    }
    public Optional<Participant> findByLogin(String login) {
        String hql = "FROM Participant p where p.login =: login";

        Query query = connector.getSession().createQuery(hql);

        query.setParameter("login", login);

        try {
            Participant participant = (Participant) query.getSingleResult();
            return Optional.ofNullable(participant);
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }


}
