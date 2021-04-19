package ru.job4j.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.model.Item;
import ru.job4j.util.HibernateUtil;

import java.util.List;
import java.util.function.Function;

/**
 * Хранение записей в базе данных
 */
public class ItemDaoImpl implements ItemDao {
    private final SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    public void save(Item item) {
        this.tx(
                session -> {
                    session.save(item);
                    return true;
                }
        );
    }

    @Override
    public void update(Item item) {
        this.tx(
                session -> {
                    session.update(item);
                    return true;
                }
        );
    }

    @Override
    public void delete(Item item) {
        this.tx(
                session -> {
                    session.delete(item);
                    return true;
                }
        );
    }

    @Override
    public List<Item> findAll() {
        return this.tx(
                session -> session.createQuery(
                        "from ru.job4j.model.Item i order by i.done", Item.class)
                        .list()
                );
    }

    @Override
    public List<Item> findAllUndone() {
        return this.tx(
                session -> session.createQuery(
                        "from ru.job4j.model.Item i where i.done = :isDone", Item.class)
                        .setParameter("isDone", false)
                        .list()
        );
    }

    @Override
    public Item findById(long id) {
        return this.tx(
                session -> session.get(Item.class, id)
        );
    }

    private <T> T tx(final Function<Session, T> command) {
        T result;
        Transaction transaction = null;
        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            result = command.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
