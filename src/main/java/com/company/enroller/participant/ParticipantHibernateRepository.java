package com.company.enroller.participant;

import com.company.enroller.persistence.DatabaseConnector;
import com.company.enroller.persistence.HibernateRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
public class ParticipantHibernateRepository extends HibernateRepository {

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


}
