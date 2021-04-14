package ru.job4j.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.model.Item;
import ru.job4j.util.HibernateUtil;

import java.util.List;

/**
 * Хранение записей в базе данных
 */
public class ItemDaoImpl implements ItemDao {
    private final SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    public void save(Item item) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.save(item);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void update(Item item) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.update(item);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void delete(Item item) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.delete(item);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Item> findAll() {
        List<Item> result;
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            result = session.createQuery("from ru.job4j.model.Item i order by i.done", Item.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
        return result;
    }

    @Override
    public List<Item> findAllUndone() {
        List<Item> result;
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            result = session.createQuery(
                    "from ru.job4j.model.Item i where i.done = :isDone", Item.class)
                    .setParameter("isDone", false)
                    .list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
        return result;
    }

    @Override
    public Item findById(long id) {
        Item result;
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            result = session.get(Item.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
        return result;
    }
}
