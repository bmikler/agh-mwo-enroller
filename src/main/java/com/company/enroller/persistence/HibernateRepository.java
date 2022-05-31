package com.company.enroller.persistence;

import org.hibernate.Session;
import org.hibernate.Transaction;
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

        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(t);
        transaction.commit();

    }

    public <T> void update(T t) {

        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().update(t);
        transaction.commit();

    }

    public <T> void delete(T t) {

        Transaction transaction = connector.getSession().beginTransaction();;
        connector.getSession().delete(t);
        transaction.commit();

    }


}
