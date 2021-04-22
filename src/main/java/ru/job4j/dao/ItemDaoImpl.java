package ru.job4j.dao;

import org.hibernate.SessionFactory;
import ru.job4j.model.Item;
import ru.job4j.model.User;
import ru.job4j.util.HibernateUtil;
import java.util.List;

/**
 * Хранение записей в базе данных
 */
public class ItemDaoImpl implements ItemDao {
    private final SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    public void save(Item item) {
        transaction(session -> {
            session.save(item);
            return true;
        }, sf);
    }

    @Override
    public void update(Item item) {
        transaction(session -> {
            session.update(item);
            return true;
        }, sf);
    }

    @Override
    public void delete(Item item) {
        transaction(session -> {
            session.delete(item);
            return true;
        }, sf);
    }

    @Override
    public List<Item> findAll() {
        return transaction(session -> session.createQuery(
                   "from ru.job4j.model.Item i order by i.done", Item.class)
                   .list(), sf);
    }

    @Override
    public List<Item> findAll(User user) {
        return transaction(session -> session.createQuery(
                "from ru.job4j.model.Item i where i.user = :user order by i.done", Item.class)
                .setParameter("user", user)
                .list(), sf);
    }

    @Override
    public List<Item> findAllUndone() {
        return transaction(session -> session.createQuery(
                   "from ru.job4j.model.Item i where i.done = :isDone", Item.class)
                   .setParameter("isDone", false)
                   .list(), sf);
    }

    @Override
    public List<Item> findAllUndone(User user) {
        return transaction(session -> session.createQuery(
                "from ru.job4j.model.Item i where i.user = :user and i.done = :isDone", Item.class)
                .setParameter("user", user)
                .setParameter("isDone", false)
                .list(), sf);
    }

    @Override
    public Item findById(long id) {
        return transaction(session -> session.get(Item.class, id), sf);
    }
}
