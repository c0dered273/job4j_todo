package ru.job4j.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.function.Function;

public interface Dao {
    default <T> T transaction(final Function<Session, T> action, SessionFactory sf) {
        T result;
        Transaction tx = null;
        try (var session = sf.openSession()) {
            tx = session.beginTransaction();
            result = action.apply(session);
            tx.commit();
            return result;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}
