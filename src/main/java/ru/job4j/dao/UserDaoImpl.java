package ru.job4j.dao;

import org.hibernate.SessionFactory;
import ru.job4j.model.User;
import ru.job4j.util.HibernateUtil;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    public void save(User user) {
        transaction(session -> {
            session.save(user);
            return true;
        }, sf);
    }

    @Override
    public void update(User user) {
        transaction(session -> {
            session.update(user);
            return true;
        }, sf);
    }

    @Override
    public void delete(User user) {
        transaction(session -> {
            session.delete(user);
            return true;
        }, sf);
    }

    @Override
    public List<User> findAll() {
        return transaction(session -> session.createQuery(
                "from ru.job4j.model.User", User.class)
                .list(), sf);
    }

    @Override
    public User findById(long id) {
        return transaction(session -> session.get(User.class, id), sf);
    }
}
