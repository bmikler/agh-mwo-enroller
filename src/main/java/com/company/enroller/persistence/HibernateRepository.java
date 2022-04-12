package com.company.enroller.persistence;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;
import java.util.List;

public abstract class HibernateRepository {

    protected DatabaseConnector connector;

    public HibernateRepository() {
        this.connector = DatabaseConnector.getInstance();
    }

    public <T> void create(T t){
        Session session = connector.getSession();

        Transaction transaction = session.beginTransaction();
        session.persist(t);
        transaction.commit();

    }

    public <T> Collection<T> readAll(Class<T> t){

        Session session = connector.getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(t);
        criteria.from(t);
        List<T> data = session.createQuery(criteria).getResultList();

        return data;

    }

    public <T> void update(T t) {

        Session session = connector.getSession();

        Transaction transaction = session.beginTransaction();
        session.update(t);
        transaction.commit();

    }

    public <T> void delete(T t) {

        Session session = connector.getSession();

        Transaction transaction = session.beginTransaction();
        session.delete(t);
        transaction.commit();

    }


}
