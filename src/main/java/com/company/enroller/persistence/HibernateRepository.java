package com.company.enroller.persistence;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.Collection;

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

        String hql = "FROM :type";
        Query query = connector.getSession().createQuery(hql);
        query.setParameter("type", t);

        return query.list();

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
