package ru.job4j.dao;

import java.util.function.Function;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Общий интерфейс для доступа к сущностям.
 */
public interface Dao {
    /**
     * Общая реализация hibernate транзакции.
     *
     * @param action Операция, которую нужно выполнить в рамках транзакции.
     * @param sf Hibernate SessionFactory.
     * @param <T> Тип результата выполнения операции.
     * @return Результат выполнения операции.
     */
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
